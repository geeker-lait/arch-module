package org.arch.ums.account.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 用户注册成功返回信息
 * @author YongWu zheng
 * @version V2.0  Created by 2020.12.28 11:39
 */
@Data
public class AuthRegAppDto extends AuthRegWebDto {

    private static final long serialVersionUID = -7953228309397753374L;
    /**
     * 客户端 ID
     */
    @NotNull(message = "客户端 ID 不能为空")
    private String clientId;
    /**
     * 客户端 secret
     */
    @NotNull(message = "客户端 SECRET 不能为空")
    private String clientSecret;

}