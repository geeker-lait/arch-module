package com.unichain.pay.channel.mfe88.dto;

import lombok.Data;
import org.arch.payment.sdk.PayResponse;

/**
 * 基础reponse bean
 */
@Data
//@EqualsAndHashCode(callSuper = true)
public class BasePayResponse extends PayResponse {

    /**
     * 交易结果code .
     */
    private String ret_code;

    /**
     * 交易结果描述 .
     */
    private String ret_msg;

    /**
     * 签名方式 .
     */
    private String sign_type;

    /**
     * 签名 .
     */
    private String sign;

    /**
     * 商户编号 .
     */
    private String oid_partner;


}
