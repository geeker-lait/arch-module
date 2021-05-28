//package com.unichain.pay.sharelink.domain;
//
//import cn.hutool.core.date.DateUtil;
//import cn.hutool.core.util.IdUtil;
//import com.unichain.pay.core.PayParam;
//import com.unichain.pay.core.PayRequest;
//import com.unichain.pay.sharelink.utils.Base64Utils;
//import com.unichain.pay.sharelink.utils.RSAUtils;
//import lombok.Data;
//import org.apache.commons.lang3.StringUtils;
//
//import java.math.BigDecimal;
//import java.util.Map;
//
//@Data
//public class ShareLinkPrePayParam implements PayParam {
//
//    /**
//     * 版本
//     */
//    private String version = "1.0.1";
//    /**
//     * 商户号
//     */
//    private String merchantId;
//    /**
//     * 商户支付订单号
//     */
//    private String payOrderId;
//    /**
//     * 请求时间
//     */
//    private String reqDate = DateUtil.date().toString("yyyyMMdd HH:mm:ss");
//    /**
//     * 金额
//     */
//    private BigDecimal amount;
//    /**
//     * 协议号
//     */
//    private String protocolId;
//    /**
//     * 客户姓名
//     */
//    private String name;
//    /**
//     * 交易账号
//     */
//    private String account;
//    /**
//     * 支付场景
//     */
//    private String payType;
//    /**
//     * 订单详情
//     */
//    private String orderDesc;
//    /**
//     * CVV2
//     */
//    private String cvv2;
//    /**
//     * 有效期
//     */
//    private String validDate;
//    /**
//     * 签名信息
//     */
//    private String sign;
//
//
//    @Override
//    public PayParam convert(Map<String, Object> map, PayRequest payRequest) {
//        this.payOrderId = payRequest.getPaymentId();
//        this.amount = map.get("amount") != null ? (BigDecimal) map.get("amount") : new BigDecimal(1.00);
//        this.name = String.valueOf(map.get("userName"));
//        this.account = String.valueOf(map.get("bankcard"));
//        this.payType = "100001";
//        this.orderDesc = "1#1#"+String.valueOf(map.get("productName"))+"^"+String.valueOf(map.get("amount"))+"^1";
//        this.cvv2 = map.get("cvv2") != null ? map.get("cvv2").toString() : "";
//        this.validDate = map.get("validDate") != null ? map.get("validDate").toString() : "";
//        return this;
//
//    }
//
//    @Override
//    public PayParam encrypt(String encryptKey) {
//        this.cvv2 = encode(this.cvv2, encryptKey);
//        this.validDate = encode(this.validDate, encryptKey);
//        return this;
//    }
//
//    public String encode(String content, String encryptKey) {
//
//        if (StringUtils.isNotBlank(content)) {
//            try {
//                byte[] contentBytes = content.getBytes(Base64Utils.UTF_8);
//                byte[] encodeBytes = RSAUtils.encryptByPublicKey(contentBytes, encryptKey);
//                return Base64Utils.encode(encodeBytes);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//        return null;
//    }
//}
