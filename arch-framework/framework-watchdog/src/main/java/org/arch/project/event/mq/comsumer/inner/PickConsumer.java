package org.arch.project.event.mq.comsumer.inner;

import org.arch.project.event.mq.comsumer.MsgConsumable;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author lait.zhang@gmail.com
 * @Tel 15801818092
 * @date 11/23/2020 1:14 PM
 * @description: 与 com.yonghui.arch.event.mq.producer.inner.PickProducer 为订阅通知的关系
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "${rocketmq.producer.topic}", consumerGroup = "${rocketmq.producer.group}")
public class PickConsumer implements RocketMQListener<String> , MsgConsumable {

    @Override
    public void onMessage(String msg) {

        log.info("收到消息:" + msg);
        // 处理转换
        Object objMsg = null;
        costMsg(objMsg);
    }


    @Override
    public void costMsg(Object msg) {
        log.info("处理消耗消息:" + msg);
    }
}
