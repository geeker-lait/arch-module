package org.arch.project.event.listener;

import org.arch.project.event.api.FulfilTicket;
import org.arch.project.event.config.WorkConfiguration;
import org.arch.project.event.event.FulfilEvent;
import org.arch.project.event.service.FulfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;

/**
 * @author lait.zhang@gmail.com
 * @Tel 15801818092
 * @date 11/23/2020 9:57 AM
 * @description: TODO
 */
public class CoreWorkListener implements SmartApplicationListener {

    @Autowired
    private WorkConfiguration workConfiguration;

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
        return workConfiguration.getStoreCoreWorkProperties().getGroupOrder();
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

        //转换事件类型
        FulfilEvent fulfilEvent = (FulfilEvent) applicationEvent;
        //获取注册用户对象信息
        FulfilTicket fulfilTicket = fulfilEvent.getFulfilTicket();
    }
}
