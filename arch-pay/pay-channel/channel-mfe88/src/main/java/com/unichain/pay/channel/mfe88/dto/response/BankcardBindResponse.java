package com.unichain.pay.channel.mfe88.dto.response;

import com.unichain.pay.channel.mfe88.dto.BasePayResponse;
import lombok.Data;

/**
 * 签约支付验证
 */
@Data
public class BankcardBindResponse extends BasePayResponse {

    private String merchantNo;
    private String dealCode;

    private String dealMsg;
    private String orderNo;
    private String cxOrderNo;
    private String sign;


}
