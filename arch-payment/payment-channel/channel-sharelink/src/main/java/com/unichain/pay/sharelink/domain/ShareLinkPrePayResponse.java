package com.unichain.pay.sharelink.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShareLinkPrePayResponse {

    //  商户号
    private String merchantId;
    //  商户支付订单号
    private String payOrderId;
    //  金额
    private BigDecimal amount;
    //  协议号
    private String protocolId;
    //  响应时间
    private String respDate;
    //  交易结果
    private String retFlag;
    //  结果代码
    private String resultCode;
    //  结果描述
    private String resultMsg;
    //  客户姓名
    private String name;
    //  交易账号
    private String account;
    //  讯联智付交易流水号
    private String serialNo;
    //  短信发送编号
    private String smsSendNo;
    //  签名信息
    private String sign;

}
