//package com.unichain.pay.channel.mfe88.dto.request;
//
//import cn.hutool.core.date.DateUtil;
//import com.unichain.pay.channel.mfe88.demo.Demo;
//import lombok.Data;
//import org.arch.framework.utils.BankCardNoUtil;
//import org.arch.payment.sdk.DirectiveRequest;
//import org.arch.payment.sdk.PayRequest;
//
//import java.util.Map;
//
///**
// * 绑卡申请
// *
// * @date 2018-8-31 03:30:56
// */
//@Data
//public class PreBindCardParams implements DirectiveRequest {
//
//
//    private String service = "quickPayBind";
//
//    /**
//     * 商户号
//     */
//    private String merchantNo;
//
//    /**
//     * 版本号
//     */
//    private String version = "V3.0";
//
//    /**
//     * 订单来源 1Pc 2手机
//     */
//    private Integer orderSource = 2;
//
//    /**
//     * 银行code
//     */
//    private String payChannelCode;
//
//    /**
//     * 1为储蓄卡；6为信用卡
//     */
//    private Integer payChannelType = 1;
//
//    /**
//     * 商户订单号
//     */
//    private String orderNo;
//
//
//    /**
//     * 订单金额 以分为单位
//     */
//    private Long orderAmount = 0L;
//
//    /**
//     * 币种
//     */
//    private String curCode = "CNY";
//
//    /**
//     * format = yyyyMMddHHmmss
//     */
//    private String orderTime;
//
//    /**
//     * 银行卡号 aes
//     */
//    private String bankCardNo;
//
//    /**
//     *
//     */
//    private String idType = "01";
//
//    /**
//     * 姓名 aes
//     */
//    private String userName;
//
//    /**
//     * 证件号 aes
//     */
//    private String idCode;
//
//    /**
//     * 电话 aes
//     */
//    private String phone;
//
//    /**
//     *
//     */
//    private String signVersion = "V3.0";
//
//    private String riskVersion = "V3.0";
//
//    private String sign;
//
//    private Integer signType = 3;
//
//    private String cvv2 = "123";
//
//    private String validPeriod = "123";
//
//    @Override
//    public DirectiveRequest convert(Map<String, Object> map, PayRequest payRequest) {
//        //String aesKey = map.get("aesKey").toString();
//        this.idCode = map.get("idCard").toString();
//        this.userName = map.get("userName").toString();
//        this.bankCardNo = map.get("bankcard").toString();
//        this.phone = map.get("phone").toString();
//        this.payChannelCode = BankCardNoUtil.getBankCardNo(map.get("bankcard").toString());
//
//        this.orderTime = DateUtil.date().toString("yyyyMMddHHmmss");
//        return this;
//    }
//
//
//    @Override
//    public DirectiveRequest decrypt(String encryptKey) {
//        this.idCode = Demo.AESDncode2(idCode, encryptKey);
//        this.userName = Demo.AESDncode2(userName, encryptKey);
//        this.bankCardNo = Demo.AESDncode2(bankCardNo, encryptKey);
//        this.phone = Demo.AESDncode2(phone, encryptKey);
//        return this;
//    }
//
//    @Override
//    public DirectiveRequest encrypt(String encryptKey) {
//        this.idCode = Demo.AESEncode(encryptKey, idCode);
//        this.userName = Demo.AESEncode(encryptKey, userName);
//        this.bankCardNo = Demo.AESEncode(encryptKey, bankCardNo);
//        this.phone = Demo.AESEncode(encryptKey, phone);
//        return this;
//    }
//
//
//}
