package org.arch.payment.sdk;

import com.alibaba.fastjson.JSONObject;
import lombok.ToString;

/**
 * 支付请求
 */
@ToString
public class PayRequest {
    // 指令码
    private DirectiveCode directiveCode;


    // 设备号
    private String deviceId;
    // ip地址
    private String ip;


    // 实体用户对应的-账号ID
    private String accountId;
    // 实体用户对应的-用户ID
    private String userId;
    // 当前账号对应的支付订单ID
    private String orderId;
    // 通道ID
    private String channelId;
    // 支付流水号
    private String paySn;
    // 金额
    private String amount;

    // 具体支付参数
    private JSONObject payParams;

    private PayRequest() {

    }


    public static PayRequest of(DirectiveCode directiveCode) {
        PayRequest payRequest = new PayRequest();
        payRequest.directiveCode = directiveCode;
        return payRequest;
    }

    /**
     * 创建map请求参数
     *
     * @param payParams
     * @return
     */
    public PayRequest init(PayParams payParams) {

        this.payParams = payParams.getParams();

//        payParams = getRequestParam(httpServletRequest);
//
//        appId = payParams.getString("appId");
//        // 移除
//        Object oAccountId = payParams.remove("accountId");
//        Object oCompanyId = payParams.remove("merchantId");
//        if (null != oAccountId && StringUtils.isNotBlank(oAccountId.toString())) {
//            accountId = oAccountId.toString();
//        }
//
//        if (null != oCompanyId && StringUtils.isNotBlank(oCompanyId.toString())) {
//            merchantId = oCompanyId.toString();
//        }
//
//
//        payType = payParams.getObject("payType");
//        if (PayType.THIRD.toString().equalsIgnoreCase(payType)) {
//            bankcard = payParams.getString("bankcard");
//        } else if (PayType.ALIPAY.toString().equalsIgnoreCase(payType)) {
//            // 获取具体的支付宝账户，这里默认先写死
//            bankcard = "alipay";
//        } else if (PayType.WEIXIN.toString().equalsIgnoreCase(payType)) {
//            // 获取具体的微信账户，这里默认先写死
//            bankcard = "wxpay";
//        }
        return this;
    }

    public PayRequest setDirectiveCode(DirectiveCode directiveCode) {
        this.directiveCode = directiveCode;
        return this;
    }
    public PayRequest setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }
    public PayRequest setIp(String ip) {
        this.ip = ip;
        return this;
    }
    public PayRequest setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }
    public PayRequest setPaymentId(String paySn) {
        this.paySn = paySn;
        return this;
    }
    public PayRequest setChannelId(String channelId) {
        this.channelId = channelId;
        return this;
    }
    public PayRequest setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }
    public PayRequest setUserId(String userId) {
        this.userId = userId;
        return this;
    }
    public PayRequest setAmount(String amount) {
        this.amount = amount;
        return this;
    }
    public PayRequest setPaySn(String paySn) {
        this.paySn = paySn;
        return this;
    }







    public DirectiveCode getDirectiveCode() {
        return directiveCode;
    }
    public String getDeviceId() {
        return deviceId;
    }
    public String getIp() {
        return ip;
    }
    public String getOrderId() {
        return orderId;
    }
    public String getPaySn() {
        return paySn;
    }
    public String getChannelId() {
        return channelId;
    }
    public String getAccountId() {
        return accountId;
    }
    public String getUserId() {
        return userId;
    }
    public String getAmount() {
        return amount;
    }
    public JSONObject getPayParams() {
        return payParams;
    }
}
