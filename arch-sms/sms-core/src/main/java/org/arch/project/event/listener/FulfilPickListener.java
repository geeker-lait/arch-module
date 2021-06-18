//package com.yonghui.arch.event.listener;
//
//import com.yonghui.arch.event.properties.WorkSeqConfig;
//import com.yonghui.arch.event.event.FulfilEvent;
//import com.yonghui.arch.event.service.FulfilService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationEvent;
//import org.springframework.context.event.SmartApplicationListener;
//import org.springframework.stereotype.Component;
//
///**
// * @author lait.zhang@gmail.com
// * @Tel 15801818092
// * @date 11/16/2020 4:18 PM
// * @description: 履约捡货监听器
// */
//@Component
//public class FulfilPickListener implements SmartApplicationListener {
//
//    @Autowired
//    private WorkSeqConfig workSeqConfig;
//
//
//    /**
//     * 事件发布是由ApplicationContext对象管控的，
//     * 我们发布事件前需要注入ApplicationContext对象调用publishEvent方法完成事件发布。
//     */
//    @Autowired
//    ApplicationContext applicationContext;
//
//
//    @Override
//    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
//        //只有FulfilEvent监听类型才会执行下面逻辑
//        return aClass == FulfilEvent.class;
//    }
//
//    @Override
//    public boolean supportsSourceType(Class<?> sourceType) {
//        //只有在FulfilService内发布的FulfilEvent事件时才会执行下面逻辑
//        return sourceType == FulfilService.class;
//    }
//
//    @Override
//    public int getOrder() {
//        return workSeqConfig.getPick();
//    }
//
//    /**
//     * supportsEventType & supportsSourceType 两个方法返回true时调用该方法执行业务逻辑
//     * @param applicationEvent
//     */
//    @Override
//    public void onApplicationEvent(ApplicationEvent applicationEvent) {
//
//
//        applicationContext.publishEvent(applicationEvent);
//
//    }
//}
