package com.unichain.pay.yeepay.domain;

import lombok.Data;

/**
 * 签约支付验证
 */
@Data
public class YeepayPayResponse {

    /**
     * 商户编号
     */
    private String merchantno;
    /**
     * 首次 还款请求号
     */
    private String requestno;
    /**
     * 易宝流水号
     */
    private String yborderid;
    /**
     * 还款金额
     */
    private String amount;
    /**
     * 订单状态
     * TO_VALIDATE：待短验确认
     * PAY_FAIL：支付失败
     * PROCESSING：处理中
     * TIME_OUT：超时失败
     * ﻿FAIL：系统异常
     */
    private String status;
    /**
     * 错误﻿码
     */
    private String errorcode;

    /**
     * 错误信息
     */
    private String errormsg;

    private String state;
}
