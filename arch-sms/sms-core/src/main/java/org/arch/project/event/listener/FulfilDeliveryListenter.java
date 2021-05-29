//package com.yonghui.arch.event.listener;
//
//import com.yonghui.arch.event.api.FulfilTicket;
//import com.yonghui.arch.event.properties.WorkSeqConfig;
//import com.yonghui.arch.event.event.FulfilEvent;
//import com.yonghui.arch.event.service.FulfilService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationEvent;
//import org.springframework.context.event.SmartApplicationListener;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//
///**
// * @author lait.zhang@gmail.com
// * @Tel 15801818092
// * @date 11/16/2020 4:19 PM
// * @description: 履约配送监听
// * 注意：如果存在多个监听同一个事件时，并且存在异步与同步同时存在时则不存在执行顺序
// */
//@Component
//public class FulfilDeliveryListenter implements SmartApplicationListener {
//
//    @Autowired
//    private WorkSeqConfig workSeqConfig;
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
//        return workSeqConfig.getDelivery();
//    }
//
//    @Override
//    @Async
//    public void onApplicationEvent(ApplicationEvent applicationEvent) {
//
//        try {
//            Thread.sleep(3000);//静静的沉睡3秒钟
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        //转换事件类型
//        FulfilEvent fulfilEvent = (FulfilEvent) applicationEvent;
//        //获取注册用户对象信息
//        FulfilTicket fulfilTicket = fulfilEvent.getFulfilTicket();
//
//        System.out.println("线程ID：" + Thread.currentThread().getId() + " 线程名称：" + Thread.currentThread().getName() + " 静静的沉睡3秒钟,ApplicationAsyncRegisterListener->来自：" + fulfilTicket.getSource() + "的捡货成功");
//
//    }
//}
