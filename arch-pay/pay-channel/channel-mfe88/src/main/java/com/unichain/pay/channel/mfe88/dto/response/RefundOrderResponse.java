package com.unichain.pay.channel.mfe88.dto.response;

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
