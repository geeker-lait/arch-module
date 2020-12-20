package com.unichain.pay.huifu.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class HuifuBaseReqDTO implements Serializable {

    /**
     * 版本号 目前固定为10
     **/
    private String version;
    /**
     * 消息类型
     **/
    private String cmd_id;
    /**
     * 商户客户号
     **/
    private String mer_cust_id;


}
