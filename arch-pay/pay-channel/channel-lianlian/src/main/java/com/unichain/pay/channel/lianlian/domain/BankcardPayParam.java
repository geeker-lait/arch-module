//package com.unichain.pay.channel.lianlian.domain;
//
//
//import com.unichain.pay.core.PayParam;
//import com.unichain.pay.core.PayRequest;
//
//import java.util.Map;
//
///**
// * 签约支付验证 请求
// *
// * @date
// */
//public class BankcardPayParam extends BasePayParam {
//    /**
//     * 商户唯一订单号 商户系统唯一订单号
//     */
//    private String no_order;
//
//    /**
//     * token 授权码
//     */
//    private String token;
//
//    /**
//     * 交易金额 该笔订单的资金总额，单位为RMB-元。大于0的数字，精确到小数点后两位。如：49.65
//     */
//    private String money_order;
//
//    /**
//     * 短信验证码
//     */
//    private String verify_code;
//
//
//    public String getNo_order() {
//        return no_order;
//    }
//
//    public void setNo_order(String no_order) {
//        this.no_order = no_order;
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public String getMoney_order() {
//        return money_order;
//    }
//
//    public void setMoney_order(String money_order) {
//        this.money_order = money_order;
//    }
//
//    public String getVerify_code() {
//        return verify_code;
//    }
//
//    public void setVerify_code(String verify_code) {
//        this.verify_code = verify_code;
//    }
//
//    @Override
//    public PayParam convert(Map<String, Object> map, PayRequest payRequest) {
//        return null;
//    }
//}
