package org.arch.payment.sdk.bean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 支付响应
 */
@Data
public class PayResponse implements Serializable {
    private Object data;
    //  返回码 讯联 1127 手机号码与预留时不符
    private String code;
    // 通道
    private String channelCode;
    // 指令名
    private String directiveName;
    //  支付订单编号
    private String paymentId;
    //  状态 失败：FAIL,成功：SUCCESS
    private String status;
    //  是否已经绑过卡
    private Boolean hasBindCard;
    //  后续流程处理状态
    //  0：需要接收短信验证码，且需要调用快捷协议支付确认支付接口；
    //  1：不需要接收短信验证码，且需要调用快捷协议支付确认支付接口；
    //  3：不需要调用快捷协议支付确认支付接口；
    private Integer needSms;
    //  返回结果
    private String resultMsg;
    private BigDecimal amount;

    public static PayResponse ok() {
        PayResponse payResponse = new PayResponse();
        return payResponse;
    }

    public static PayResponse error() {
        PayResponse payResponse = new PayResponse();
        return payResponse;
    }

    public PayResponse data(Object t) {
        this.data = t;
        return this;
    }

}
