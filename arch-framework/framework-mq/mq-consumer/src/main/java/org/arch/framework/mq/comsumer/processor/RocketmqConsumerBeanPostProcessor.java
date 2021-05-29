package org.arch.framework.mq.comsumer.processor;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.arch.framework.config.ComponentConfig;
import org.arch.framework.config.ComponentConfigHolder;
import org.arch.framework.config.MiddlewareConfig;
import org.arch.framework.config.model.RocketmqConsumerConfigModel;
import org.arch.framework.mq.comsumer.annotation.EnableRocketConsumer;
import org.arch.framework.mq.comsumer.annotation.RocketConcurrentlyListener;
import org.arch.framework.mq.messaging.M2Utils;
import org.arch.framework.mq.messaging.MqMessage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class RocketmqConsumerBeanPostProcessor implements BeanPostProcessor, DisposableBean, InitializingBean {

    private Map<String, DefaultMQPushConsumer> consumerMap = new HashMap<>();
    private List<DefaultMQPushConsumer> consumers = new ArrayList<>();
    private List<RocketmqConsumerConfigModel> rocketmqConsumerConfigModels;
    private List<String> groups = new ArrayList<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> aClass = bean.getClass();
        EnableRocketConsumer enableRocketConsumer = aClass.getAnnotation(EnableRocketConsumer.class);
        int counter = 0;
        if (enableRocketConsumer != null) {
            Method[] methods = aClass.getMethods();
            /* check  */
            final List<Method> annMethods = Arrays.stream(methods).filter(method -> method.isAnnotationPresent(RocketConcurrentlyListener.class)
                    || method.isAnnotationPresent(RocketConcurrentlyListener.class))
                    .collect(Collectors.toList());
            if (annMethods.size() > 1) {
                throw new BeanCreationException("一个消费组只允许订阅一个topic,只能有一个订阅方法，存在多个订阅方法的" + aClass);
            }

            for (Method method : methods) {
                RocketConcurrentlyListener rocketConcurrentlyListener = method.getAnnotation(RocketConcurrentlyListener.class);
                if (rocketConcurrentlyListener != null) {
                    processConcurrentlyWithMessage2(bean, aClass, enableRocketConsumer, method, rocketConcurrentlyListener);
                }
            }
        }
        return bean;
    }




    private String getAddress(String cluster) {
        final List<RocketmqConsumerConfigModel> vos = rocketmqConsumerConfigModels.stream().filter(v -> {
            final String vCluster = v.getBeanSuffix();
            if (StringUtils.isBlank(cluster)) {
                if (StringUtils.isBlank(vCluster)) {
                    return true;
                }
                return false;
            } else {
                return cluster.equals(vCluster);
            }
        }).collect(Collectors.toList());
        if (vos.isEmpty()) {
            throw new BeanCreationException("cannot found  rocketmq consumer properties");
        }
        if (vos.size() > 1) {
            throw new BeanCreationException("found  rocketmq consumer properties > 1, found size " + vos.size());
        }
        final RocketmqConsumerConfigModel rocketMQSpringConsumerVo = vos.get(0);
        String address = rocketMQSpringConsumerVo.getAddress();
        log.info("found cluster : {} rocketmq address : {}", StringUtils.isBlank(cluster) ? "NULL" : cluster, address);
        return address;
    }

    private void processConcurrentlyWithMessage2(Object bean, Class<?> aClass, EnableRocketConsumer enableRocketConsumer, Method method, RocketConcurrentlyListener consumerAnnotation) {
        String topic = consumerAnnotation.value();
        if (StringUtils.isBlank(topic)) {
            throw new IllegalArgumentException(String.format("topic is required by %s.%s RocketConcurrentlyListener", aClass.getName(), method.getName()));
        }
        String oldGroup = enableRocketConsumer.value();
        if (StringUtils.isBlank(oldGroup)) {
            if (StringUtils.isBlank(enableRocketConsumer.value())) {
                throw new IllegalArgumentException(String.format("group is required by %s.%s @RocketConcurrentlyListener ", aClass.getName(), method.getName()));
            }
        }
        String group = formatGroup(oldGroup, aClass);

        int parameterCount = method.getParameterCount();
        if (parameterCount != 1) {
            throw new BeanCreationException(String.format("method %s can only a one parameter and type must be  com.yonghui.common.message.bridge.model.Message2", method.getName()));
        }
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (!parameterTypes[0].isAssignableFrom(MqMessage.class)) {
            throw new BeanCreationException(String.format("method %s can only a one parameter and type must be  com.yonghui.common.message.bridge.model.Message2", method.getName()));
        }

        ConsumeFromWhere consumeFromWhere = consumerAnnotation.consumeFromWhere();
        final int consumeThreadMin = consumerAnnotation.consumeThreadMin();
        final int consumeThreadMax = consumerAnnotation.consumeThreadMin();
        final String cluster = consumerAnnotation.withCluster();
        String address = getAddress(cluster);
        startConsumer(topic, group, consumeFromWhere, consumeThreadMin, consumeThreadMax, address, (msgs, context) -> {
            for (MessageExt messageExt : msgs) {
                try {
                    final MqMessage message2 = M2Utils.unserialize(messageExt.getBody());
                    if (method == null) {
                        log.warn("收到取消订阅的消息 topic is ：" + topic);
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    final Object message = method.invoke(bean, message2);
                    ConsumeConcurrentlyStatus status = (ConsumeConcurrentlyStatus) message;
                    if (!ConsumeConcurrentlyStatus.CONSUME_SUCCESS.equals(status)) {
                        return status;
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
    }

    private void processConcurrently(Object bean, Class<?> aClass, EnableRocketConsumer enableRocketConsumer, Method method, RocketConcurrentlyListener consumerAnnotation) {
        String topic = consumerAnnotation.value();
        if (StringUtils.isBlank(topic)) {
            throw new IllegalArgumentException(String.format("topic is required by %s.%s RocketConcurrentlyListener", aClass.getName(), method.getName()));
        }
        final String oldGroup = enableRocketConsumer.value();
        if (StringUtils.isBlank(oldGroup)) {
            throw new IllegalArgumentException(String.format("group is required by %s.%s @RocketConcurrentlyListener ", aClass.getName(), method.getName()));
        }
        String group = formatGroup(oldGroup, aClass);

        int parameterCount = method.getParameterCount();
        if (parameterCount != 1) {
            throw new BeanCreationException(String.format("method %s can only a one parameter and type must be  com.alibaba.rocketmq.common.message.MessageExt", method.getName()));
        }
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (!parameterTypes[0].isAssignableFrom(MessageExt.class)) {
            throw new BeanCreationException(String.format("method %s can only a one parameter and type must be  com.alibaba.rocketmq.common.message.MessageExt", method.getName()));
        }

        ConsumeFromWhere consumeFromWhere = consumerAnnotation.consumeFromWhere();
        final int consumeThreadMin = consumerAnnotation.consumeThreadMin();
        final int consumeThreadMax = consumerAnnotation.consumeThreadMin();
        final String cluster = consumerAnnotation.withCluster();
        final String address = getAddress(cluster);
        startConsumer(topic, group, consumeFromWhere, consumeThreadMin, consumeThreadMax, address, (msgs, context) -> {
            for (MessageExt messageExt : msgs) {
                try {
                    final Object message = method.invoke(bean, messageExt);
                    ConsumeConcurrentlyStatus status = (ConsumeConcurrentlyStatus) message;
                    if (!ConsumeConcurrentlyStatus.CONSUME_SUCCESS.equals(status)) {
                        return status;
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
    }

    private void startConsumer(String topic, String group, ConsumeFromWhere consumeFromWhere, int consumeThreadMin, int consumeThreadMax, String address, MessageListenerConcurrently messageListener) {
        try {

            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
            consumer.setConsumerGroup(group);
            consumer.setNamesrvAddr(address);
            consumer.subscribe(topic, "*");
            consumer.setConsumeFromWhere(consumeFromWhere);
            consumer.setConsumeThreadMin(consumeThreadMin);
            consumer.setConsumeThreadMax(consumeThreadMax);
            consumer.registerMessageListener(messageListener);
            consumer.setInstanceName(address);
            log.info("starting group {} topic {} consumer {}", group, topic, consumer);
            consumer.start();
            consumerMap.put(String.format("%s#%s", group, topic), consumer);
            log.info("started group {} topic {} consumer {}", group, topic, consumer);
            consumers.add(consumer);
        } catch (MQClientException e) {
            throw new BeanCreationException(e.getErrorMessage(), e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ComponentConfig midConfig = ComponentConfigHolder.get();
        MiddlewareConfig config = midConfig.getConfig(midConfig.getAppId(), midConfig.getSecret());
        rocketmqConsumerConfigModels = config.getRocketmqConsumerConfigModels();
        if (rocketmqConsumerConfigModels.isEmpty()) {
            throw new NullPointerException("未找到消费组配置，appid是 " + midConfig.getAppId());
        }
    }


    @Override
    public void destroy() throws Exception {
        for (DefaultMQPushConsumer consumer : consumers) {
            try {
                consumer.shutdown();
            } catch (Exception e) {
                // ig
            }
        }
    }

    private String formatGroup(String oldGroup, Class<?> aClass) {
        String group;
        if (groups.contains(oldGroup)) {
            throw new BeanCreationException("consumer group 不允许重复，重复的consumer group名 :" + oldGroup + "class ：" + aClass);
        } else {
            groups.add(oldGroup);
            if (oldGroup.contains("%")) {
                group = oldGroup;
                log.info("Using Group : {}", group);
            } else {
                group = ComponentConfigHolder.get().getApplicationName() + "%" + oldGroup;
                log.info("不存在百分号，自动拼接项目名到group前 : {}", group);
            }
        }
        return group;
    }
}
