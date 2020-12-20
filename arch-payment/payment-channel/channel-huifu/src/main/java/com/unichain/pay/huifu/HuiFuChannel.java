//package com.unichain.pay.huifu;
//
//import com.unichain.pay.core.ChannelDirectiveConfigable;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.stereotype.Component;
//
//@Component("huiFuPayChannel")
//public class HuiFuChannel {
//    @Autowired
//    private ApplicationContext applicationContext;
//    private String payName="huiFuPay";
//    @Override
//    public Payable create() {
//        return applicationContext.getBean(getPayName(),Payable.class);
//    }
//
//    @Override
//    public String getPayName() {
//        return payName;
//    }
//
//    @Override
//    public void setPayName(String payName) {
////        this.payName = payName;
//    }
//
//    @Override
//    public ChannelDirectiveConfigable getChannelConfiguration() {
//        return null;
//    }
//}
