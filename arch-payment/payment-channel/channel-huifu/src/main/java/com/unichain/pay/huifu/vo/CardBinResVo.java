package com.unichain.pay.huifu.vo;

import lombok.Data;

/**
 * @author: PC
 * @version: v1.0
 * @description: com.unichain.pay.base.payapi.huifupay.vo
 * @date:2019/3/27
 */
@Data
public class CardBinResVo extends HuifuBaseLoanResp {

    private String card_no;

    private String card_bin;

    private String bank_name;

    private String card_type;

    private String bank_no;

    private String response_code;

    private String response_message;
}
