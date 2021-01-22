package com.unichain.pay;


import lombok.Data;

/**
 * 绑卡验证响应对象
 * <p>
 * 应答样例:
 * {
 * "ret_code":"0000",
 * "ret_msg":交易成功",
 * "oid_partner":"201103171000000000",
 * "user_id":"2013051500001",
 * "card_no ":"622202112313213123",
 * "no_agree":"2013051613121201",
 * "sign_type":"RSA",
 * "sign":"ZPZULntRpJwFmGNIVKwjLEF2Tze7bqs60rxQ22CqT5J1UlvGo575QK9z/
 * +p+7E9cOoRoWzqR6xHZ6WVv3dloyGKDR0btvrdqPgUAoeaX/YOWzTh00vwcQ+HBtX
 * E+vPTfAqjCTxiiSJEOY7ATCF1q7iP3sfQxhS0nDUug1LP3OLk="
 * }
 */
@Data
public class BankcardBindVerifyResponse {

    /**
     * 商户编号是商户在连连支付支付平台上开设的
     * 商 户 号 码 ， 为 18 位 数 字 ， 如 ：
     * 201304121000001004
     */
    private String ret_code;

    /**
     * 签约接口返回的授权令牌。超过 30 分钟，或
     * 者其他原因导致上送的授权令牌失效，需重新
     * 发起签约接口获取授权令牌
     */
    private String ret_msg;

    /**
     * 签名方式
     */
    private String sign_type;
    /**
     * RSA 加密签名，见安全签名机制
     */
    private String sign;
    ///////////////////////////////支付结果信息///////////////////////////
    /**
     * 商户编号
     */
    private String oid_partner;

    /**
     * 签约订单号
     */
    private String no_order;
    /**
     * 商户用户唯一编号 保证唯一
     */
    private String user_id;
    /**
     * 银行卡号
     */
    private String card_no;

    /**
     * 签约协议号
     * 支付成功后返回本次签约协议号
     */
    private String no_agree;

}
