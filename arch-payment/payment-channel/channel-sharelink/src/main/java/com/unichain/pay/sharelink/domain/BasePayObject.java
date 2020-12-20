package com.unichain.pay.sharelink.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class BasePayObject implements Serializable {
    //R		报文摘要签名
    protected String sign;
    //R		商户编号
    protected String merchantId;


}
