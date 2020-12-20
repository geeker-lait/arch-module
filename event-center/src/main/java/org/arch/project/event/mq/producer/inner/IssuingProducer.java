package org.arch.project.event.mq.producer.inner;

import org.arch.project.event.mq.producer.MsgProducable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author lait.zhang@gmail.com
 * @Tel 15801818092
 * @date 11/23/2020 1:18 PM
 * @description: TODO
 */
@Slf4j
@Component("issuing")
public class IssuingProducer implements MsgProducable {

    @Override
    public void sendMsg(String topic, String msg) {

    }
}
