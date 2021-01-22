package com.unichain.pay.sharelink.domain;

import lombok.Data;

@Data
public class RefundResponse {

    private String merchantId;
    private String refundOrderId;
    private String amount;
    private String respDate;
    private String retFlag;
    private String resultCode;
    private String resultMsg;
    private String account;
    private String serialNo;
    private String reserved;
    private String sign;


}
