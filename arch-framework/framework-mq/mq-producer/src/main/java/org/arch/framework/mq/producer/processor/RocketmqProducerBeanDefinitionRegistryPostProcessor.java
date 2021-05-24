package org.arch.framework.mq.producer.processor;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import org.apache.commons.lang3.StringUtils;
import org.arch.framework.beans.utils.ValidateUtils;
import org.arch.framework.config.ComponentConfig;
import org.arch.framework.config.ComponentConfigHolder;
import org.arch.framework.config.ComponentInitException;
import org.arch.framework.config.MiddlewareConfig;
import org.arch.framework.config.model.RocketmqProducerConfigModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class RocketmqProducerBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    private static final Logger log = LoggerFactory.getLogger(RocketmqProducerBeanDefinitionRegistryPostProcessor.class);

    public RocketmqProducerBeanDefinitionRegistryPostProcessor() {
    }

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        ComponentConfig midConfig = ComponentConfigHolder.get();
        MiddlewareConfig config = midConfig.getConfig(midConfig.getAppId(), midConfig.getSecret());
        List<RocketmqProducerConfigModel> rocketMQProducerVos = config.getRocketmqProducerConfigModels();
        if (rocketMQProducerVos.isEmpty()) {
            throw new ComponentInitException("must be exist rocket mq producer component config");
        } else {
            Iterator<RocketmqProducerConfigModel> var = rocketMQProducerVos.iterator();

            while (var.hasNext()) {
                RocketmqProducerConfigModel producerVo = var.next();
                String suffix = producerVo.getBeanSuffix() == null ? "" : producerVo.getBeanSuffix();
                ValidateUtils.validate(producerVo);
                String connectionFactoryBeanName = "defaultMQProducer" + suffix;
                boolean value = producerVo.getRetryAnotherBrokerWhenNotStore() == null ? false : producerVo.getRetryAnotherBrokerWhenNotStore();
                new DefaultMQProducer();
                AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
                        .rootBeanDefinition(DefaultMQProducer.class)
                        .addPropertyValue("namesrvAddr", producerVo.getAddress())
                        .addPropertyValue("retryAnotherBrokerWhenNotStoreOK", value)
                        .addPropertyValue("maxMessageSize", producerVo.getMaxMessageSize())
                        .addPropertyValue("producerGroup", midConfig.getApplicationName() + "-" + UUID.randomUUID().toString())
                        .addPropertyValue("instanceName", connectionFactoryBeanName + UUID.randomUUID())
                        .getBeanDefinition();
                beanDefinition.setInitMethodName("start");
                beanDefinition.setLazyInit(false);
                registry.registerBeanDefinition(connectionFactoryBeanName, beanDefinition);
                if (StringUtils.isBlank(suffix)) {
                    beanDefinition.setPrimary(true);
                    log.info("arch middleware dynamic registerBeanDefinition [{}] isPrimary [{}] ,Using producer config [{}]", new Object[]{connectionFactoryBeanName, true, producerVo});
                } else {
                    log.info("arch middleware dynamic registerBeanDefinition [{}] isPrimary [{}] ,Using producer config [{}]", new Object[]{connectionFactoryBeanName, false, producerVo});
                }
            }

        }
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }
}
