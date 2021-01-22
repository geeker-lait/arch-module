package com.unichain.pay;

import lombok.Data;

/**
 * 签约支付响应参数
 * 应答样例:
 * {
 * "ret_code":"0000",
 * "ret_msg":交易成功",
 * "token":"D096DBA0E3E0CC8F1D504C06E71D292D",
 * "oid_partner":"201103171000000000",
 * "dt_order":"20130515094013",
 * "no_order":"2013051500001",
 * "oid_paybill":"2013051613121201",
 * "money_order":"210.97",
 * "sign_type":"RSA",
 * "sign":"ZPZULntRpJwFmGNIVKwjLEF2Tze7bqs60rxQ22CqT5J1UlvGo575QK9z/+p+7
 * E9cOoRoWzqR6xHZ6WVv3dloyGKDR0btvrdqPgUAoeaX/YOWzTh00vwcQ+HBtXE+vPTfAq
 * jCTxiiSJEOY7ATCF1q7iP3sfQxhS0nDUug1LP3OLk="
 * }
 */
@Data
public class BankcardRepayResponse {

    /**
     * 交易结果代码
     * 0000 交易成功
     * 8888 需要再次进行验证
     * 见返回码
     */
    private String ret_code;

    /**
     * 交易结果描述
     * 商户用户唯一编号 保证唯一
     */
    private String ret_msg;

    /**
     * 授权码
     * 支付授权令牌。交易代码为 8888 是非空，
     * 超过 30 分钟失效
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
     * 商户唯一订单号
     * 商户系统唯一订单号
     */
    private String no_order;

    /**
     * 商户订单时间
     * 格式：YYYYMMDDH24MISS 14 位数
     * 字，精确到秒
     */
    private String dt_order;

    /**
     * 交易金额
     * 该笔订单的资金总额，单位为 RMB-元。
     * 大于 0 的数字，精确到小数点后两位。
     * 如：49.65（两次调用都必须送）
     */
    private String money_order;

    /**
     * 连连支付支付单号
     * 2011030900001098
     * 订单创建成功返回
     */
    private String oid_paybill;

    /**
     * 订单描述
     */
    private String info_order;


    /**
     * 签约协议号
     * 支付成功后返回本次签约协议号
     */
    private String no_agree;

    /**
     * 清算日期
     * YYYYMMDD 支付成功后会有
     */
    private String settle_date;

}
