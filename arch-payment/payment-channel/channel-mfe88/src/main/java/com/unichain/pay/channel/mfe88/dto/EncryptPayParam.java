package com.unichain.pay.channel.mfe88.dto;


/**
 * uber加密请求
 */
public abstract class EncryptPayParam extends BasePayParam {

    /**
     * 商户编号 .
     */
    private String oid_partner;

    /**
     * 加密串 ,使用连连加密工具包生成的加密串
     */
    private String pay_load;


    public String getOid_partner() {
        return oid_partner;
    }

    public void setOid_partner(String oid_partner) {
        this.oid_partner = oid_partner;
    }

    public String getPay_load() {
        return pay_load;
    }

    public void setPay_load(String pay_load) {
        this.pay_load = pay_load;
    }
}
