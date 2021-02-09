package org.arch.framework.api;

import java.util.concurrent.TimeUnit;

public enum IdKey {
    /**
     * app
     */
    APP_ID("id.app.", 1, TimeUnit.DAYS, "0", "%1$06d"),

    // =================== UMS系统ID ===================
    /**
     * 账户
     */
    UMS_ACCOUNT_ID("id.account.", 60, TimeUnit.SECONDS, "1", "%1$07d"),
    /**
     * 用户
     */
    UMS_USER_ID("id.account.", 60, TimeUnit.SECONDS, "2", "%1$07d"),
    /**
     * 会员
     */
    UMS_MEMBER_ID("id.account.", 60, TimeUnit.SECONDS, "5", "%1$07d"),
    /**
     * 商户
     */
    UMS_MERCHANT_ID("id.account.", 60, TimeUnit.SECONDS, "7", "%1$07d"),
    /**
     * 账号-标识/AccountIdentifier
     */
    UMS_ACCOUNT_IDENTIFIER_ID("id.identifier.", 60, TimeUnit.SECONDS, "8", "%1$07d"),


    // =================== OMS系统ID ===================
    /**
     * 订单
     */
    OMS_ORDER_ID("id.order.", 60, TimeUnit.SECONDS, "6", "%1$07d"),


    // =================== PMS系统ID ===================
    PMS_PRODUCT_ID("id.product.", 60, TimeUnit.SECONDS, "4", "%1$07d"),


    // =================== PAY系统ID ===================
    /**
     * 绑卡
     */
    BANGKCARD_ID("id.bankcard.", 60, TimeUnit.SECONDS, "3", "%1$07d"),
    /**
     * 产品
     */
    /* 绑卡ID */
    PAY_BINDCARD_ID("id.pay.bindcard.", 60, TimeUnit.SECONDS, "10", "%1$07d"),
    /**
     * 代扣ID
     */
    PAY_WITHHOLD_ID("id.pay.withold.", 60, TimeUnit.SECONDS, "11", "%1$07d"),
    /**
     * 支付ID
     */
    PAY_ORDER("id.pay.order", 60, TimeUnit.SECONDS, "12", "%1$07d"),
    /**
     * 退款订单ID
     */
    PAY_REFUND_ID("id.pay.refund.", 60, TimeUnit.SECONDS, "13", "%1$07d"),
    /**
     * 代付
     */
    PAY_FOR_ANOTHER_ID("id.pay.for.another.", 60, TimeUnit.SECONDS, "14", "%1$07d"),
    /**
     * 还款订单号
     */
    REPAY("id.pay.repay.", 60, TimeUnit.SECONDS, "15", "%1$07d"),


    // =================== 支付系统ID ===================
    /**
     * 账单
     */
    BILL("id.bill.", 60, TimeUnit.SECONDS, "20", "%1$07d"),
    /**
     * 评估报告ID
     */
    ASSESS("id.assess.", 60, TimeUnit.SECONDS, "21", "%1$07d"),
    /**
     * 推荐ID
     */
    RECOMMEND("id.recommend.", 60, TimeUnit.SECONDS, "22", "%1$07d"),

    // =================== jwt ID ===================
    /**
     * JWT jti
     */
    JWT_JTI("id.jwt.", 60, TimeUnit.SECONDS, "30", "%1$07d"),
    /**
     * JWT refresh Token
     */
    JWT_REFRESH_TOKEN("id.jwt.refresh.token.", 60, TimeUnit.SECONDS, "31", "%1$07d"),
    ;

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
