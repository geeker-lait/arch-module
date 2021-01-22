package com.unichain.pay.huifu.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: PC
 * @version: v1.0
 * @description: com.unichain.pay.base.payapi.huifupay.vo 汇付公共响应参数
 * @date:2019/3/26
 */
@Data
public class HuifuBaseLoanResp implements Serializable {
    //请求流水
    private String request_seq;
    //请求日期
    private String request_date;
    //返回码
    private String response_code;
    //描述
    private String response_message;
    //签名，参考签名算法; 异步返回时验签，同步无该字段
    private String signature;
}
