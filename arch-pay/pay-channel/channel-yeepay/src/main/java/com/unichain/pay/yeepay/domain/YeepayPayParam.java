//package com.unichain.pay.yeepay.domain;
//
//
//import com.unichain.pay.core.PayParam;
//import com.unichain.pay.core.PayRequest;
//import lombok.Data;
//
//import java.util.Map;
//
///**
// * 支付确认
// *
// * @date
// */
//@Data
//public class YeepayPayParam implements PayParam {
//
//
//    private String requestno;
//    private String validatecode;
//
//    @Override
//    public void setSign(String prikeyvalue) {
//
//    }
//
//    @Override
//    public PayParam convert(Map<String, Object> map, PayRequest payRequest) {
//
//        this.requestno = map.get("orderNo").toString();
//        this.validatecode = map.get("smsCode").toString();
//
//        return this;
//    }
//
//    @Override
//    public PayParam encrypt(String encryptKey) {
//        return this;
//    }
//
//    @Override
//    public void decryptParam() {
//
//    }
//}
