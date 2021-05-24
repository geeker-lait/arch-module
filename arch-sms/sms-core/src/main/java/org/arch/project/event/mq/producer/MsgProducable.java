package org.arch.project.event.mq.producer;

/**
 * @author lait.zhang@gmail.com
 * @Tel 15801818092
 * @date 11/23/2020 1:42 PM
 * @description: TODO
 */
public interface MsgProducable {

    void sendMsg(String topic, String msg);
}
