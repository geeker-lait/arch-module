package org.arch.project.event.listener;

import org.arch.project.event.api.FulfilTicket;
import org.arch.project.event.config.WorkConfiguration;
import org.arch.project.event.event.FulfilEvent;
import org.arch.project.event.mq.producer.MsgProducable;
import org.arch.project.event.service.FulfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;

/**
 * @author lait.zhang@gmail.com
 * @Tel 15801818092
 * @date 11/23/2020 9:52 AM
 * @description: TODO
 */
public class StoreInnerWorkListener implements SmartApplicationListener {

    @Autowired
    private WorkConfiguration workConfiguration;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
        //只有FulfilEvent监听类型才会执行下面逻辑
        return aClass == FulfilEvent.class;
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        //只有在FulfilService内发布的FulfilEvent事件时才会执行下面逻辑
        return sourceType == FulfilService.class;
    }

    @Override
    public int getOrder() {
        return workConfiguration.getStoreInnerWorkProperties().getGroupOrder();
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        //转换事件类型
        FulfilEvent fulfilEvent = (FulfilEvent) applicationEvent;
        //获取注册用户对象信息
        FulfilTicket fulfilTicket = fulfilEvent.getFulfilTicket();
        // 获取作业阶段
        String workPhase = fulfilTicket.getFulfilPhase().getVal().split(":")[1];
        // mq topic 发送消息解耦
        MsgProducable msgProducable = applicationContext.getBean(workPhase,MsgProducable.class);
        // 发送消息(每个阶段做各自的事情)
        msgProducable.sendMsg(workPhase,fulfilTicket.toString());
    }
}
