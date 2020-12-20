package com.unichain.pay;


/**
 * https://mpayapi.lianlianpay.com/v1/bankcardbindverfy
 * 请求参数采用 json 报文进行，样例如下：
 * {
 * "oid_partner":"201103171000000000",
 * "token":"D096DBA0E3E0CC8F1D504C06E71D292D",
 * "user_id":"2013051500001",
 * "verify_code":"666666",
 * "sign_type ":"RSA",
 * "sign":"ZPZULntRpJwFmGNIVKwjLEF2Tze7bqs60rxQ22CqT5J1UlvGo575QK9z/
 * +p+7E9cOoRoWzqR6xHZ6WVv3dloyGKDR0btvrdqPgUAoeaX/YOWzTh00vwcQ+HBtXE+vP
 * TfAqjCTxiiSJEOY7ATCF1q7iP3sfQxhS0nDUug1LP3OLk="
 * }
 */
public class BankcardBindVerifyRequest {

    /**
     * 商户编号是商户在连连支付支付平台上开设的
     * 商 户 号 码 ， 为 18 位 数 字 ， 如 ：
     * 201304121000001004
     */
    private String oid_partner;

    /**
     * 签约接口返回的授权令牌。超过 30 分钟，或
     * 者其他原因导致上送的授权令牌失效，需重新
     * 发起签约接口获取授权令牌
     */
    private String token;

    /**
     * 签名方式
     */
    private String sign_type;
    /**
     * RSA 加密签名，见安全签名机制
     */
    private String sign;
    /**
     * 商户签约鉴权唯一订单号，若传该字段，将会
     * 与签约申请时签约订单号进行比对
     */
    private String no_order;
    /**
     * 商户用户唯一编号 保证唯一
     */
    private String user_id;
    /**
     * 短信验证码，下发到用户预留手机号码中的验
     * 证码
     */
    private String verify_code;

}
