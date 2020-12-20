package com.unichain.pay;

import lombok.Data;

/**
 * 签约支付验证响应参数
 * <p>
 * 应答样例:
 * {
 * "dt_order": "20180828153543",
 * "money_order": "55.44",
 * "no_agree": "2017092339974797",
 * "no_order": "20180828153543",
 * "oid_partner": "201304121000001004",
 * "oid_paybill": "2018082971158876",
 * "ret_code": "0000",
 * "ret_msg": "交易成功",
 * "settle_date": "20180829",
 * "sign":
 * "BdKDxbxqRQlTq/aV9KxYYFV6qVAgy+Z2u5MkefDjcwsshkWK6H6WtEr59AApGKgdOU5/
 * DD6aw0TOeZBYba+zQpAFHSmhsTsoC2tiUoi8dWNXc2rARVmC4HdSoUpyr39ICO3PL/Swq
 * yWwmolZEu1LR5/SqYw/n7glDY0jsq2ghxA=",
 * "sign_type": "RSA"
 * }
 */
@Data
public class BankcardPayResponse {

    /**
     * 交易结算商户编号
     * 商户编号是商户在连连支付支付平台上开设结
     * 算到银行账户的商户号码，为 18 位数字，如：
     * 201304121000001004
     */
    private String ret_code;

    /**
     * 交易结果描述
     */
    private String ret_msg;

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


    ///////////////////////////支付结果信息/////////////////////////////

    /**
     * 商户编号
     * 商户编号是商户在连连支付支付平台上开
     * 设的商户号码，为 18 位数字，如：
     * 201304121000001004
     */
    private String oid_partner;

    /**
     * 商户订单时间
     * 格式：YYYYMMDDH24MISS 14 位数
     * 字，精确到秒
     */
    private String dt_order;


    /**
     * 商户唯一订单号
     * 商户系统唯一订单号
     */
    private String no_order;


    /**
     * 连连支付支付单号
     */
    private String oid_paybill;

    /**
     * 交易金额
     * 该笔订单的资金总额，单位为 RMB-元。
     * 大于 0 的数字，精确到小数点后两位。
     * 如：49.65
     */
    private String money_order;


    /**
     * 清算日期
     * YYYYMMDD 支付成功后会有
     */
    private String settle_date;


    /**
     * 订单描述
     */
    private String info_order;

    /**
     * 签约协议号
     * 支付成功后返回本次签约协议号
     */
    private String no_agree;


}
