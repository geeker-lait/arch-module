package org.arch.project.event.mq.producer.inner;

import org.arch.project.event.mq.producer.MsgProducable;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lait.zhang@gmail.com
 * @Tel 15801818092
 * @date 11/23/2020 1:14 PM
 * @description: 仓内作业生成pick作业
 */
@Slf4j
@Component("pick")
public class PickProducer implements MsgProducable {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    //@Autowired
    //private MessageSendPerformer messageSendPerformer;

    @Override
    public void sendMsg(String topic, String msg) {
        log.info("发送报文：" + msg);
        this.rocketMQTemplate.convertAndSend(topic, msg);
    }
}