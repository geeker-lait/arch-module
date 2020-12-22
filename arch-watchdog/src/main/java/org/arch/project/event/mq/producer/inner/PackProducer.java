package org.arch.project.event.mq.producer.inner;

import org.arch.project.event.mq.producer.MsgProducable;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component("pack")
public class PackProducer implements MsgProducable {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void sendMsg(String topic, String msg) {
        log.info("发送报文：" + msg);
        this.rocketMQTemplate.convertAndSend(topic, msg);
    }
}