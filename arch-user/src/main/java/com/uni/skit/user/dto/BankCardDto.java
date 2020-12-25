package com.uni.skit.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankCardDto {

    private Long id;
    @ApiModelProperty(value = "姓名", required = true)
    @NotNull(message = "姓名不能为空")
    private String name;

    @ApiModelProperty(value = "身份证号", required = true)
    @NotNull(message = "身份证号不能为空")
    private String idCard;

    @ApiModelProperty(value = "银行卡号", required = true)
    @NotNull(message = "银行卡号不能为空")
    private String bankCard;

    @ApiModelProperty(value = "银行名称")
    private String bankName;

    @ApiModelProperty(value = "手机号", required = true)
    @NotNull(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty(value = "验证码")
    private String captcha;

    @ApiModelProperty(value = "绑卡Id")
    private String bankCardId;

}
