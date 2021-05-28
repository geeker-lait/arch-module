//package com.unichain.pay.sharelink.domain;
//
//import cn.hutool.core.date.DateUtil;
//import cn.hutool.core.util.IdUtil;
//import com.unichain.pay.core.PayParam;
//import com.unichain.pay.core.PayRequest;
//import com.unichain.pay.sharelink.utils.SzsharelinkBankCodeUtil;
//import lombok.Data;
//
//import java.util.Map;
//
//@Data
//public class BankcardBindParam implements PayParam {
//
//    private String version = "1.0.1";
//    private String merchantId;
//    private String merOrderId;
//    private String organCode;
//    private String organName;
//    private String reqDate = DateUtil.date().toString("yyyyMMdd HH:mm:ss");
//    private String name;
//    private String account;
//    private String accountType = "90";
//    private String idType = "10";
//    private String idCode;
//    private String mobile;
//    private String cvv2;
//    private String validDate;
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
//        merOrderId = payRequest.getPaymentId();
//        this.idCode = map.get("idCard").toString();
//        this.name = map.get("userName").toString();
//        String bankcard = map.get("bankcard").toString();
//        this.account = bankcard;
//        this.organCode = SzsharelinkBankCodeUtil.getByCard(bankcard);
//        this.mobile = map.get("phone").toString();
//        return this;
//    }
//
//    @Override
//    public PayParam encrypt(String encryptKey) {
//        return null;
//    }
//}
