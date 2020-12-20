package com.unichain.pay;

import lombok.Data;

/**
 * 签约支付验证
 * https://mpayapi.lianlianpay.com/v1/bankcardpay
 * <p>
 * 样例(本报文不涉及加密):
 * {
 * "money_order": "55.44",
 * "no_order": "20180828153543",
 * "oid_partner": "201304121000001004",
 * "sign":
 * "3DdWXwYzWuXHZ0oCc1vDDEKIMv0QpjSfAYXV6t8nTjR0QcAWvN65BL4zS+Csys1Hx1rd
 * AciMqg4yoevq4jxHOPi4T3h1ryld51x0uUGzcyeymp8aQGtkexiZPwxLTdQ62KR7czfeb
 * wbUt5uDny5dstIZ26Bupfm3YgBFb3kdiIg=",
 * "sign_type": "RSA",
 * "token": "E4A27991786F7114DCAF88C266A465C2",
 * "verify_code": "123456"
 * }
 */
@Data
public class BankcardPayRequest {

    /**
     * 交易结算商户编号
     * 商户编号是商户在连连支付支付平台上开设结
     * 算到银行账户的商户号码，为 18 位数字，如：
     * 201304121000001004
     */
    private String oid_partner;

    /**
     * 授权码
     * 支付授权令牌。就是银行卡支付预处理成功返
     * 回的支付授权令牌。超过 30 分钟，或者其他
     * 原因导致上送的支付授权令牌失效，需要重新
     * 对该订单做银行卡支付预处理，重新获取支付
     * 授权令牌
     */
    private String token;

    /**
     * 签名方式
     */
    private String sign_type;

    /**
     * 签名
     */
    private String sign;


    ///////////////////////////交易要素/////////////////////////////


    /**
     * 商户唯一订单号
     * 商户系统唯一订单号
     */
    private String no_order;


    /**
     * 交易金额
     * 该笔订单的资金总额，单位为 RMB-元。
     * 大于 0 的数字，精确到小数点后两位。
     * 如：49.65
     */
    private String money_order;


    /**
     * 短信验证码
     * 短信验证码，下发到用户预留手机号码中的验证码
     */
    private String verify_code;


}
