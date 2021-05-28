//package com.unichain.pay.sharelink.domain;
//
//import cn.hutool.core.date.DateUtil;
//import cn.hutool.core.util.IdUtil;
//import com.unichain.pay.core.PayParam;
//import com.unichain.pay.core.PayRequest;
//import lombok.Data;
//
//import java.math.BigDecimal;
//import java.util.Map;
//
//@Data
//public class ShareLinkPayParam implements PayParam {
//
//    //版本
//    private String version = "1.0.1";
//    //商户号
//    private String merchantId;
//    //商户支付订单号
//    private String payOrderId;
//    //请求时间
//    private String reqDate = DateUtil.date().toString("yyyyMMdd HH:mm:ss");
//    //金额
//    private BigDecimal amount;
//    //协议号
//    private String protocolId;
//    //短信发送编号
//    private String smsSendNo;
//    //短信验证码
//    private String smsVerifyCode;
//    //CVV2
//    private String cvv2;
//    //有效期
//    private String validDate;
//    //签名信息
//    private String sign;
//
//
//    @Override
//    public PayParam convert(Map<String, Object> map, PayRequest payRequest) {
//
//        this.payOrderId = String.valueOf(map.get("paymentId"));
//        this.amount = map.get("amount") != null ? (BigDecimal) map.get("amount") : new BigDecimal(100.00);
//        this.smsVerifyCode = String.valueOf(map.get("smsCode"));
//        this.cvv2 = map.get("cvv2") != null ? map.get("cvv2").toString() : null;
//        this.validDate = map.get("validDate") != null ? map.get("validDate").toString() : null;
//        return this;
//    }
//
//    @Override
//    public PayParam encrypt(String encryptKey) {
//        return null;
//    }
//}
