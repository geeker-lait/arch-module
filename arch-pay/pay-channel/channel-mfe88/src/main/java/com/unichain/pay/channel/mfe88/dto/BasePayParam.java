package com.unichain.pay.channel.mfe88.dto;

import lombok.Data;
import org.arch.payment.sdk.DirectiveRequest;
import org.arch.payment.sdk.PayRequest;
import org.springframework.beans.BeanUtils;

import java.util.Map;

/**
 * 基础request bean
 */
@Data
public abstract class BasePayParam implements DirectiveRequest {

    /**
     * 商户编号 .
     */
    public String oid_partner;

    /**
     * 用户来源 .
     */
    public String platform;


    /**
     * 签名方式 .
     */
    public String sign_type;

    /**
     * 签名方 .
     */
    public String sign;

    private String service;

    public DirectiveRequest convert(Map<String, Object> map, PayRequest payRequest) {
        BeanUtils.copyProperties(map, this);
        return this;
    }

    @Override
    public DirectiveRequest encrypt(String encryptKey) {
        return null;
    }
}
