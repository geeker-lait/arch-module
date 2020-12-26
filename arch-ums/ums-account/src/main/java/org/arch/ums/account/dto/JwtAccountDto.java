package org.arch.ums.account.dto;

import lombok.Data;

/**
 * Description:
 *
 * @author kenzhao
 * @date 2020/3/28 16:19
 */
@Data
public class JwtAccountDto {
    private Long id;
    private String password;
    /**
     * 账户名,手机号
     */
    private String accountName;
}