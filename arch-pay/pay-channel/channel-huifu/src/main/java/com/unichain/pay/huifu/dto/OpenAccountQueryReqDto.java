package com.unichain.pay.huifu.dto;

import lombok.Data;

/**
 * @author: PC
 * @version: v1.0
 * @description: com.unichain.pay.base.payapi.huifupay.dto 放款开户查询
 * @date:2019/3/26
 */
@Data
public class OpenAccountQueryReqDto extends HuifuBaseLoanReqDto {
    /**
     * 该笔请求流水号
     */
    private String bind_request_seq;
    /**
     * 00-身份证
     */
    private String cert_type;
    /**
     * 身份证号
     */
    private String cert_id;
    /**
     * 银行卡号
     */
    private String card_no;
}
