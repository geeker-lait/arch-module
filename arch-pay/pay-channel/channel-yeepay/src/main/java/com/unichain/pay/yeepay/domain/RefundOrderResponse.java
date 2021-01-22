package com.unichain.pay.yeepay.domain;

import lombok.Data;

@Data
public class RefundOrderResponse {

    private String merchantNo;
    private String dealCode;
    private String dealMsg;
    private String orderNo;
    private String cxOrderNo;
    private String sign;

}
