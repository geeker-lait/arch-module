package com.unichain.pay.huifu.dto;

import lombok.Data;

/**
 * @author: PC
 * @version: v1.0
 * @description: com.unichain.pay.base.payapi.huifupay.dto
 * @date:2019/3/27
 */
@Data
public class OpenAccLoanDto extends HuifuBaseLoanReqDto {
    //证件类型 00-身份证
    private String cert_type;
    //身份证
    private String cert_id;
    //用户姓名
    private String user_name;
    //卡号
    private String card_no;
    //卡类型 D借机 C贷记
    private String card_type;
    //银行代码，参考附录
    private String bank_id;
    //银行预留手机号
    private String bank_mobile;
    //银行所在省，参考附录
    private String bank_province_id;
    //银行所在地区，参考附录(不传时，少数放款业务可能会受影响)
    private String bank_area_id;
    //绑卡结果可能为异步，需接受异步应答
    private String bg_return_url;
}
