package com.unichain.pay;

/**
 * 应答样例:
 * {
 * "ret_code":"8888",
 * "ret_msg":"短信已下发，需要再次验证",
 * "token":"D096DBA0E3E0CC8F1D504C06E71D292D",
 * "oid_partner":"201103171000000000",
 * "user_id":"20130515094013",
 * "sign_type":"RSA",
 * "sign":"ZPZULntRpJwFmGNIVKwjLEF2Tze7bqs60rxQ22CqT5J1UlvGo575QK9z/
 * +p+7E9cOoRoWzqR6xHZ6WVv3dloyGKDR0btvrdqPgUAoeaX/YOWzTh00vwcQ+HBtX
 * E+vPTfAqjCTxiiSJEOY7ATCF1q7iP3sfQxhS0nDUug1LP3OLk="
 * }
 */
public class BankcardBindResponse {

    /**
     * 交易结果代码  ret_code
     */
    private String ret_code;
    /**
     * 交易结果描述
     */
    private String ret_msg;
    /**
     * 授权码
     */
    private String token;
    /**
     * 签名方式
     */
    private String sign_type;
    /**
     * 签名
     * RSA 加密签名，见安全签名机制
     */
    private String sign;

    ////////////////////////////////////签约结果信息（交易成功返回）/////////////////////////////////
    /**
     * 商户编号
     * 商户编号是商户在连连支付支付平台上开
     * 设的商户号码，为 18 位数字，如：
     * 201304121000001004
     */
    private String oid_partner;
    /**
     * 签约订单号
     */
    private String no_order;
    /**
     * 签约请求时间
     * 格式：YYYYMMDDH24MISS 14 位数
     * 字，精确到秒
     */
    private String dt_order;
    /**
     * 用户唯一编号
     */
    private String user_id;
}
