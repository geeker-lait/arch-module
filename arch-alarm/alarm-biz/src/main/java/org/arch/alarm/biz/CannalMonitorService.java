package org.arch.alarm.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.api.pojo.canal.CanalMessageData;
import org.springframework.beans.BeansException;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/27/2021 10:33 PM
 */
//@EnableRocketConsumer("ofs-alarm-center%rocketmq-t1")
@Slf4j
public class CannalMonitorService implements ApplicationContextAware {
    private final static Map<String, CanalRowMonitable> MOINTER = new HashMap();

    // @RocketConcurrentlyListenerWithMessage2(value = "ofs-alarm-canal-monitor", consumeFromWhere = ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET)
    public ConsumeConcurrentlyStatus monitor(Object message) {
        //Object msg = message.getMessageParamValues().get("message");
        Object msg = null;
        ConsumeConcurrentlyStatus ccs = ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        if (null == msg || 0 == msg.toString().trim().length()) {
            log.warn("空消息");
            return ccs;
        }
        CanalMessageData canalMessageData = JSON.parseObject(msg.toString(), CanalMessageData.class);
        //log.info("收到数据{}. 解析结果{}", message.toString(), canalMessageData.toString());
        // todo 根据要监听的库获取对应的监听器
        CanalRowMonitable canalRowMonitable = MOINTER.get(canalMessageData.getDbName());
        if (null != canalRowMonitable) {
            try {
                canalRowMonitable.monitor(canalMessageData);
            } catch (Exception e) {
                log.error(e.getMessage());
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }


    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) throws Exception {
        T bean = clazz.newInstance();
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext.getBeansOfType(CanalRowMonitable.class).forEach((key, value) -> {
            MOINTER.put(value.getMonitorDatabase(), value);
        });
    }

}
