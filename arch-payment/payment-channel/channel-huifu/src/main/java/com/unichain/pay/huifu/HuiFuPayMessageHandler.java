//package com.unichain.pay.huifu;
//
//import com.unichain.pay.core.CallbackDto;
//import com.unichain.pay.core.MessageHandler;
//import org.springframework.stereotype.Component;
//
//@Component("huiFuPayMessageHandler")
//public class HuiFuPayMessageHandler implements MessageHandler {
//    @Override
//    public CallbackDto handle(String record) {
//        System.out.println(record);
//
//        CallbackDto dto = new CallbackDto();
//
//
//        dto.setPayNo("123");
//        dto.setStatus("2");
//        dto.setTradeTime("2019-01-01 11:11:11");
//        return dto;
//    }
//
//}
