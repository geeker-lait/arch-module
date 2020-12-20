package com.unichain.pay.huifu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: PC
 * @version: v1.0
 * @description: com.unichain.pay.base.payapi.huifupay.dto
 * @date:2019/3/26
 */
@Data
public class HuifuBaseLoanReqDto implements Serializable {
    //调用方唯一标识
    private String app_token;
    // 委托人名
    private String client_name;
    // 请求流水，字母数字下划线组成
    private String request_seq;
    //请求日期，格式 yyyyMMdd
    private String request_date;
    //签名
    private String signature;
}
