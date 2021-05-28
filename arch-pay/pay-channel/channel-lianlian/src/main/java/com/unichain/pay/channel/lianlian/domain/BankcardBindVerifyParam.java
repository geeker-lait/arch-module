//package com.unichain.pay.channel.lianlian.domain;
//
//
//import com.unichain.pay.core.PayParam;
//import com.unichain.pay.core.PayRequest;
//
//import java.util.Map;
//
///**
// * 签约申请验证 请求
// *
// * @date
// */
//public class BankcardBindVerifyParam extends BasePayParam {
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
//
//    /**
//     * 短信验证码
//     */
//    private String verify_code;
//    /**
//     * 用户ID
//     */
//    private String user_id;
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
//    public String getVerify_code() {
//        return verify_code;
//    }
//
//    public void setVerify_code(String verify_code) {
//        this.verify_code = verify_code;
//    }
//
//    public String getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(String user_id) {
//        this.user_id = user_id;
//    }
//
//    @Override
//    public PayParam convert(Map<String, Object> map, PayRequest payRequest) {
//        return null;
//    }
//}
