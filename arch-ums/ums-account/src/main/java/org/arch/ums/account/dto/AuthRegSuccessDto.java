package org.arch.ums.account.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求信息
 *
 * @author YongWu zheng
 * @version V2.0  Created by 2020.12.28 11:39
 */
@Data
public class AuthRegSuccessDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * accountId
     */
    private Long accountId;
    /**
     * 账号-标识 ID, 一个标识 ID 可以对应多个
     */
    private Long accountIdentifierId;

    /**
     * 账户名,邮箱,手机号,第三方用户ID
     */
    private String identifier;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;


}