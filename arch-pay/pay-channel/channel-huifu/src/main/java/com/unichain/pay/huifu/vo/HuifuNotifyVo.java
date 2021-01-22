package com.unichain.pay.huifu.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author: PC
 * @version: v1.0
 * @description: com.unichain.pay.base.payapi.huifupay.vo
 * @date:2019/4/1
 */
@Data
@ToString
public class HuifuNotifyVo implements Serializable {

    private String request_date;

    private String response_code;

    private String response_message;

    private String signature;

    private String request_seq;
}
