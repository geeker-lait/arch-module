package org.arch.framework.id;

import java.util.concurrent.TimeUnit;

public enum IdKey {
    /** app */
    APP_ID("id.app.", 1, TimeUnit.DAYS, "00", "%1$06d"),
    /** 账户 */
    ACCOUNT_ID("id.account.", 60, TimeUnit.MILLISECONDS, "01", "%1$08d"),
    /** 用户 */
    USER_ID("id.user.", 60, TimeUnit.MILLISECONDS, "02", "%1$08d"),
    /** 绑卡 */
    BANGKCARD_ID("id.bankcard.", 60, TimeUnit.MILLISECONDS, "03", "%1$08d"),
    /** 产品 */
    PRODUCT_ID("id.product.", 60, TimeUnit.MILLISECONDS, "04", "%1$08d"),
    /** 会员 */
    MEMBER_ID("id.member.", 60, TimeUnit.MILLISECONDS, "05", "%1$08d"),
    /** 订单 */
    ORDER_ID("id.order.", 60, TimeUnit.MILLISECONDS, "06", "%1$08d"),
    /** 商户 */
    MERCHANT_ID("id.merchant.", 60, TimeUnit.MILLISECONDS, "07", "%1$08d"),
    /** 账号-标识/AccountIdentifier */
    ACCOUNT_IDENTIFIER_ID("id.merchant.", 60, TimeUnit.MILLISECONDS, "08", "%1$08d"),


    // =================== 支付系统ID ===================

    /* 绑卡ID */
    PAY_BINDCARD_ID("id.pay.bindcard.", 60, TimeUnit.MILLISECONDS, "10", "%1$08d"),
    /** 代扣ID */
    PAY_WITHHOLD_ID("id.pay.withold.", 60, TimeUnit.MILLISECONDS, "11", "%1$08d"),
    /** 支付ID */
    PAY_ORDER("id.pay.order", 60, TimeUnit.MILLISECONDS, "12", "%1$08d"),
    /** 退款订单ID */
    PAY_REFUND_ID("id.pay.refund.", 60, TimeUnit.MILLISECONDS, "13", "%1$08d"),
    /** 代付 */
    PAY_FOR_ANOTHER_ID("id.pay.for.another.", 60, TimeUnit.MILLISECONDS, "14", "%1$08d"),
    /** 还款订单号 */
    REPAY("id.pay.repay.", 60, TimeUnit.MILLISECONDS, "15", "%1$08d"),

    /** 账单 */
    BILL("id.bill.", 60, TimeUnit.MILLISECONDS, "20", "%1$08d"),
    /** 评估报告ID */
    ASSESS("id.assess.", 60, TimeUnit.MILLISECONDS, "21", "%1$08d"),

    /** 推荐ID */
    RECOMMEND("id.recommend.", 60, TimeUnit.MILLISECONDS, "22", "%1$08d");

    private String key;
    private String bizPrefix;
    private int timeout;
    private String fmtSuffix;
    private TimeUnit timeUnit;


    IdKey(String key, int timeout, TimeUnit timeUnit, String bizPrefix, String fmtSuffix) {
        this.key = key;
        this.bizPrefix = bizPrefix;
        this.timeout = timeout;
        this.fmtSuffix = fmtSuffix;
        this.timeUnit = timeUnit;
    }

    public String getKey() {
        return key;
    }

    public String getBizPrefix() {
        return bizPrefix;
    }

    public int getTimeout() {
        return timeout;
    }

    public String getFmtSuffix() {
        return fmtSuffix;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

//    public static void main(String[] args) {
//        //IdBizCode.init();
//        IdKey bizIdCode = IdKey.valueOf("bill".toUpperCase());
//        System.out.println(bizIdCode);
//    }

}
