package com.unichain.pay.yeepay.domain;

import lombok.Data;

@Data
public class YeepayPrePayResponse {

    /**
     * 商户编号
     */
    private String merchantno;
    /**
     * 首次还款请求号
     */
    private String requestno;
    /**
     * 短验发送方
     * YEEPAY：易宝
     * BANK：银行
     */
    private String codesender;
    /**
     * 实际短验发送类型
     * VOICE：语音
     * MESSAGE：短信
     * NONE：无短验
     */
    private String smstype;

    /**
     * 订单状态
     * TO_VALIDATE：待短验确认
     * PAY_FAIL：支付失败
     * FAIL：
     * 系统异常
     */
    private String status;

    /**
     * 错误码
     */
    private String errorcode;
    /**
     * 错误信息
     */
    private String errormsg;

    /**
     * 分账请求易宝流水号
     * 请求参数 dividelist 非空时，有此返回值
     */
    private String divideyborderid;

    /**
     * 分账状态
     * 请求参数 dividelist 非空时，有此返回值
     * DIVIDE_PROCESSING：分账处理中
     * DIVIDE_FAIL：分账失败
     */
    private String dividestatus;

    /**
     * 分账错误码
     * 请求参数dividelist非空时，有此返回值
     * dividestatus = DIVIDE_FAIL时有值，否则为空串
     */
    private String divideerrorcode;

    /**
     * 分账错误信息
     * 请求参数dividelist非空时，有此返回值
     * dividestatus = DIVIDE_FAIL，否则为空串
     */
    private String divideerrormsg;

    private String state;

}
