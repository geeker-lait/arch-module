//package com.unichain.pay.channel.lianlian.domain;
//
//import com.unichain.pay.core.PayParam;
//import com.unichain.pay.core.PayRequest;
//
//import java.util.Map;
//
///**
// * 基础request bean
// */
//public class BasePayParam implements PayParam {
//
//    /**
//     *
//     */
//    private static final long serialVersionUID = -3882734663326736687L;
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
//    public String getOid_partner() {
//        return oid_partner;
//    }
//
//    public void setOid_partner(String oid_partner) {
//        this.oid_partner = oid_partner;
//    }
//
//    public String getPlatform() {
//        return platform;
//    }
//
//    public void setPlatform(String platform) {
//        this.platform = platform;
//    }
//
//    public String getSign_type() {
//        return sign_type;
//    }
//
//    public void setSign_type(String sign_type) {
//        this.sign_type = sign_type;
//    }
//
//    public String getSign() {
//        return sign;
//    }
//
//    public void setSign(String sign) {
//        this.sign = sign;
//    }
//
//    @Override
//    public PayParam convert(Map<String, Object> map, PayRequest payRequest) {
//        return null;
//    }
//
//    @Override
//    public PayParam encrypt(String encryptKey) {
//        return null;
//    }
//}
