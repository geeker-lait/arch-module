package org.arch.project.event.mq.comsumer;

/**
 * @author lait.zhang@gmail.com
 * @Tel 15801818092
 * @date 11/23/2020 2:23 PM
 * @description: TODO
 */
public interface MsgConsumable<T> {
    void costMsg(T msg);
}
