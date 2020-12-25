package com.uni.skit.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Description:
 *
 * @author kenzhao
 * @date 2020/3/28 16:17
 */
@Data
public class AuthAccountDto implements Serializable {
    private Long id;
    private static final long serialVersionUID = 1L;

    private String password;
    /**
     * 账户名,手机号
     */
    private String accountName;
    /**
     * 应用ID
     */
    private String appId;
    private String code;
}