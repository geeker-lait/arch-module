//package com.unichain.pay.channel.lianlian.domain;
//
//public class BankcardPrePayResponse extends BasePayResponse {
//
//    /**
//     * 商户唯一订单号 商户系统唯一订单号
//     */
//    private String no_order;
//
//    /**
//     * 商户订单时间 格式：YYYYMMDDH24MISS 14位数字，精确到秒
//     */
//    private String dt_order;
//
//
//    /**
//     * 订单描述
//     */
//    private String info_order;
//
//    /**
//     * 交易金额 该笔订单的资金总额，单位为RMB-元。大于0的数字，精确到小数点后两位。如：49.65
//     */
//    private String money_order;
//
//    /**
//     * 签约协议号
//     */
//    private String no_agree;//todo 应该是第一个免密签约支付会返回协议号吗
//    /**
//     * 授权码	支付授权令牌。交易代码为0000 是非空，超过30分钟失效
//     */
//    private String token;
//    /**
//     * 连连支付支付单号	订单创建成功返回
//     */
//    private String oid_paybill;
//
//    /**
//     * 清算日期 YYYYMMDD 0000支付成功后会有
//     */
//    private String settle_date;
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
//    public String getDt_order() {
//        return dt_order;
//    }
//
//    public void setDt_order(String dt_order) {
//        this.dt_order = dt_order;
//    }
//
//    public String getInfo_order() {
//        return info_order;
//    }
//
//    public void setInfo_order(String info_order) {
//        this.info_order = info_order;
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
//    public String getNo_agree() {
//        return no_agree;
//    }
//
//    public void setNo_agree(String no_agree) {
//        this.no_agree = no_agree;
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
//    public String getOid_paybill() {
//        return oid_paybill;
//    }
//
//    public void setOid_paybill(String oid_paybill) {
//        this.oid_paybill = oid_paybill;
//    }
//
//    public String getSettle_date() {
//        return settle_date;
//    }
//
//    public void setSettle_date(String settle_date) {
//        this.settle_date = settle_date;
//    }
//}
