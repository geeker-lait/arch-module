package org.arch.ums.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankCardDelDto {
    @NotNull(message = "银行卡号不能为空")
    private String bankCard;
}
