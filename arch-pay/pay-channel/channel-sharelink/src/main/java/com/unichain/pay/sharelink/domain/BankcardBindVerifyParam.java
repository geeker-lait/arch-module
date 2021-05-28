//package com.unichain.pay.sharelink.domain;
//
//import com.unichain.pay.core.PayParam;
//import com.unichain.pay.core.PayRequest;
//import cn.hutool.core.date.DateUtil;
//import lombok.Data;
//
//import java.util.Map;
//
//@Data
//public class BankcardBindVerifyParam implements PayParam {
//
//    private String version= "1.0.1";
//    private String merchantId;
//    private String merOrderId;
//    private String reqDate = DateUtil.date().toString("yyyyMMdd HH:mm:ss");
//    private String cvv2;
//    private String validDate;
//    private String smsSendNo;
//    private String smsVerifyCode;
//    private String sign;
//
//
//    @Override
//    public void setSign(String prikeyvalue) {
//        this.sign = prikeyvalue;
//    }
//
//    @Override
//    public PayParam convert(Map<String, Object> map, PayRequest payRequest) {
//        this.merOrderId = String.valueOf(map.get("paymentId"));
//        this.smsVerifyCode = String.valueOf(map.get("smsCode"));
//        return this;
//    }
//
//
//    @Override
//    public PayParam encrypt(String encryptKey) {
//        return null;
//    }
//
//}
