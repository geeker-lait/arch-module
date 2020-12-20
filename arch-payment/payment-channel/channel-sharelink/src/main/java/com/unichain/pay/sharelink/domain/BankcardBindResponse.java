package com.unichain.pay.sharelink.domain;

import lombok.Data;

@Data
public class BankcardBindResponse {
    private String merchantId;
    private String merOrderId;
    private String respDate;
    private String retFlag;
    private String resultCode;
    private String resultMsg;
    private String smsSendNo;
    private String protocolId;
    private String sign;
}
