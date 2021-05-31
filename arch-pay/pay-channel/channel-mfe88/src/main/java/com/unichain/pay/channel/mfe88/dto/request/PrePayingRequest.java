//package com.unichain.pay.channel.mfe88.dto.request;
//
//import cn.hutool.core.date.DateUtil;
//import com.unichain.pay.channel.mfe88.demo.Demo;
//import lombok.Data;
//import org.arch.framework.utils.AmountUtils;
//import org.arch.framework.utils.BankCardNoUtil;
//import org.arch.payment.sdk.DirectiveRequest;
//import org.arch.payment.sdk.PayRequest;
//
//import java.math.BigDecimal;
//import java.util.Map;
//
//@Data
//public class PrePayingParams implements DirectiveRequest {
//    /**
//     * 接口名称
//     */
//    private String service = "quickPayApply";
//    /**
//     * 商户编号
//     */
//    private String merchantNo;
//    /**
//     * 服务器接受支付结果的后台地址
//     */
//    private String bgUrl;
//    /**
//     * 订单来源 1PC，2手机
//     */
//    private String orderSource = "2";
//    /**
//     * 网关版本
//     */
//    private String version = "V3.0";
//    /**
//     * 签名版本
//     */
//    private String signVersion = "V3.0";
//    /**
//     * 风控版本
//     */
//    private String riskVersion = "V3.0";
//    /**
//     * 支付通道编码 填写银行编码
//     */
//    private String payChannelCode;
//    /**
//     * 支付通道类型 1为储蓄卡 6为信用
//     */
//    private String payChannelType;
//    /**
//     * 商户系统产生的唯一订单号
//     */
//    private String orderNo;
//    /**
//     * 目前只支持人民币 固定值：CNY
//     */
//    private String curCode = "CNY";
//    /**
//     * 格式：yyyyMMddHHmmss
//     */
//    private String orderTime;
//    /**
//     * 商户的商品名称，英文或中文字符串
//     */
//    private String productName;
//    /**
//     * 银行卡号：内容用AES加密发送
//     */
//    private String bankCardNo;
//    /**
//     * 付款人证件类型：01：身份证02：军官证03：护照04：回乡证05：台胞证06：警官证07：士兵证
//     */
//    private String idType = "01";
//    /**
//     * 持卡人姓名: 内容用AES加密发送
//     */
//    private String userName;
//    /**
//     * 证件号：内容用AES加密发送
//     */
//    private String idCode;
//    /**
//     * 电话：内容用AES加密发送
//     */
//    private String phone;
//    /**
//     * Cvv2 内容用AES加密发送； [payChannelType=6必填(最后3位)]
//     */
//    private String cvv2;
//    /**
//     * 信用卡有效期 内容用AES加密发送:[payChannelType=6必填(MMyy)]
//     */
//    private String validPeriod;
//    /**
//     * 订单金额 以"分"为单位的整型，必须大于零
//     */
//    private String orderAmount;
//    /**
//     * 扩展字段 1
//     */
//    private String ext1;
//    /**
//     *
//     */
//    private String ext2;
//    /**
//     * 商户签名数据
//     */
//    private String sign;
//    /**
//     * 签名类型 1: MD5； 2： RSA； 3：CFCA证书
//     */
//    private String signType = "3";
//
//    @Override
//    public DirectiveRequest convert(Map<String, Object> map, PayRequest payRequest) {
//        if (map.get("userName") != null) {
//            this.userName = map.get("userName").toString();
//        }
//        if (map.get("bankcard") != null) {
//            this.bankCardNo = map.get("bankcard").toString();
//        }
//        if (map.get("phone") != null) {
//            this.phone = map.get("phone").toString();
//        }
//        if (map.get("idCard") != null) {
//            this.idCode = map.get("idCard").toString();
//        }
//
//        this.payChannelType = map.get("payChannelType") != null ? map.get("payChannelType").toString() : "1";
//        this.orderNo = payRequest.getPaymentId();
//        this.orderTime = DateUtil.date().toString("yyyyMMddHHmmss");
//        this.productName = map.get("productName").toString();
//        this.orderAmount = AmountUtils.changeY2F(new BigDecimal(map.get("amount").toString()));
//        this.payChannelCode = BankCardNoUtil.getBankCardNo(this.bankCardNo);
//
//        return this;
//    }
//
//    @Override
//    public DirectiveRequest encrypt(String encryptKey) {
//        this.userName = Demo.AESEncode(encryptKey, userName);
//        this.bankCardNo = Demo.AESEncode(encryptKey, bankCardNo);
//        this.phone = Demo.AESEncode(encryptKey, phone);
//        this.idCode = Demo.AESEncode(encryptKey, idCode);
//        return this;
//    }
//}