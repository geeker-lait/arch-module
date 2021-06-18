//package com.unichain.pay.yeepay.domain;
//
//import com.unichain.pay.core.PayParam;
//import com.unichain.pay.core.PayRequest;
//import lombok.Data;
//import org.springframework.beans.BeanUtils;
//
//import java.util.Map;
//
///**
// * 基础request bean
// */
//@Data
//public abstract class BasePayParam implements PayParam {
//
//    /**
//     * 商户编号 .
//     */
//    public String oid_partner;
//
//    /**
//     * 用户来源 .
//     */
//    public String platform;
//
//
//    /**
//     * 签名方式 .
//     */
//    public String sign_type;
//
//    /**
//     * 签名方 .
//     */
//    public String sign;
//
//    private String service;
//
//    @Override
//    public PayParam convert(Map<String, Object> map, PayRequest payRequest) {
//        BeanUtils.copyProperties(map, this);
//        return this;
//    }
//
//    @Override
//    public PayParam encrypt(String encryptKey) {
//        return null;
//    }
//}
