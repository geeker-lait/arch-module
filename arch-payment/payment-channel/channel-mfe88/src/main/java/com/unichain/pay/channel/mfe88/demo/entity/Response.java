package com.unichain.pay.channel.mfe88.demo.entity;

import lombok.Data;

@Data
public class Response {
    String sign;
    String codeUrl;
    String dealCode;
    String dealMsg;
    String needSms;
    //search
    String orderStatus;
    String orderNo;
    String fee;
    String cxOrderNo;
    String orderAmount;
    String orderTime;
    String dealTime;
    String payChannelCode;
    String ext2;
    String ext1;
    String merchantNo;
    String refundAmount;
    String refundCount;
    String productName;
    //pay for another one search
    String accountName;
    String bankCode;
    String bankName;
    String bankNumber;
    String cause;
    String currency;
    //upp
    String code;
    String msg;
    String rechargeAmt;
    String orderFinishStatus;
    String userId;
    String orderResultTime;
    String mOrderNo;
    String orderRequestTime;

    public String getPayForAnotherOneStatus() {
        if (dealCode == null)
            return "";
        switch (dealCode) {
            case "90001":
                dealCode = "失败";
                break;
            case "10000":
                dealCode = "成功";
                break;
            case "10001":
                dealCode = "处理中";
                break;
            default:
                break;
        }
        return "<br>订单状态:" + dealCode;
    }

    public String getNeedSms() {
        if (needSms == null)
            return "";
        switch (needSms) {
            case "0":
                needSms = "<br>需要接收短信验证码，且需要调用快捷协议支付确认支付接口";
                break;
            case "1":
                needSms = "<br>不需要接收短信验证码，且需要调用快捷协议支付确认支付接口";
                break;
            case "3":
                needSms = "<br>不需要调用快捷协议支付确认支付接口";
                break;
            default:
                break;
        }
        return needSms;
    }

    public String getOrderStatus() {
        if (orderStatus == null)
            return "";
        switch (orderStatus) {
            case "0":
                orderStatus = "处理中";
                break;
            case "1":
                orderStatus = "成功";
                break;
            case "2":
                orderStatus = "失败";
                break;
            default:
                break;
        }
        orderStatus = "<br>订单状态:" + orderStatus;
        return orderStatus;
    }

    public String getOrderFinishStatus() {
        if (orderFinishStatus == null)
            return "";
        switch (orderFinishStatus) {
            case "0":
                orderFinishStatus = "处理中";
                break;
            case "1":
                orderFinishStatus = "成功";
                break;
            case "2":
                orderFinishStatus = "失败";
                break;
            default:
                break;
        }
        orderFinishStatus = "<br>订单状态:" + orderFinishStatus;
        return orderFinishStatus;
    }
}
