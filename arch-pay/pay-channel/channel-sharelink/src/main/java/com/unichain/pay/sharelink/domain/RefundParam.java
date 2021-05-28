//package com.unichain.pay.sharelink.domain;
//
//import cn.hutool.core.bean.BeanUtil;
//import cn.hutool.core.date.DateUtil;
//import com.unichain.pay.core.PayParam;
//import com.unichain.pay.core.PayRequest;
//import lombok.Data;
//
//import java.util.Map;
//
//@Data
//public class RefundParam implements PayParam {
//
//    private String version = "1.0.1";
//    private String merchantId;
//    private String refundOrderId;
//
//    private String reqDate = DateUtil.date().toString("yyyyMMdd HH:mm:ss");
//    private String amount;
//    private String orgPayOrderId;
//    private String reserved;
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
//        RefundParam refundParam = BeanUtil.mapToBean(map, this.getClass(), true);
//        BeanUtil.copyProperties(refundParam, this);
//        this.refundOrderId = payRequest.getPaymentId();
//        this.orgPayOrderId = String.valueOf(map.get("paymentId"));
//        return this;
//    }
//
//    @Override
//    public PayParam encrypt(String encryptKey) {
//        return null;
//    }
//}
