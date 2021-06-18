package org.arch.framework.ums.jwt.claim;

/**
 * 财智有道的 claim names
 * @author YongWu zheng
 * @since 2021.1.3 16:08
 */
public enum JwtArchClaimNames {

    /**
     * 账号标识 id
     */
    IDENTIFIER_ID("identifierId", "账号标识 ID"),
    /**
     * 账号 ID
     */
    ACCOUNT_ID("accountId", "账号 ID"),
    /**
     * 昵称
     */
    NICK_NAME("nickName", "昵称"),
    /**
     * 头像
     */
    AVATAR("avatar", "头像"),
    /**
     * 登录类型
     */
    LOGIN_TYPE("loginType", "登录类型"),
    /**
     * 用户 ID
     */
    USER_ID("userId", "用户 ID"),
    /**
     * 用户名 对应与 AccountIdentifier 的 identifier 字段
     */
    USERNAME("username", "用户名"),
    /**
     * 租户 ID
     */
    TENANT_ID("tenantId", "租户 ID"),
    /**
     * 应用 id
     */
    APP_ID("appId", "应用 id"),
    /**
     * user details
     */
    USER_DETAILS("userDetails", "userDetails"),
    /**
     * 用户权限
     */
    AUTHORITIES("authorities", "用户权限"),
    /**
     * refresh token jti
     */
    REFRESH_TOKEN_JTI("rJti", "refresh token jti"),
    /**
     * scope 权限, 与 scp 意义一样
     */
    SCOPE("scope", "scope 权限, 与 scp 意义一样"),
    /**
     * scope 权限, 与 scope 意义一样
     */
    SCP("scp", "scope 权限, 与 scope 意义一样");



    /**
     * claim name
     */
    private final String claimName;
    /**
     * claim name 的描述
     */
    private final String description;

    JwtArchClaimNames(String claimName, String description) {
        this.claimName = claimName;
        this.description = description;
    }

    public String getClaimName() {
        return claimName;
    }

    public String getDescription() {
        return description;
    }

}