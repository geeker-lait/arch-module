package org.arch.framework.jwt.claim;

/**
 * 财智有道的 claim names
 * @author YongWu zheng
 * @since 2021.1.3 16:08
 */
public enum JwtArchClaimNames {
    /**
     * 账号 ID
     */
    ACCOUNT_ID("accountId", "账号 ID"),
    /**
     * 用户唯一标识, 与 {@link #IDENTIFIER} 意义一样
     */
    ACCOUNT_NAME("accountName", "用户唯一标识"),
    /**
     * 昵称
     */
    NICK_NAME("nickName", "昵称"),
    /**
     * 头像
     */
    AVATAR("avatar", "头像"),
    /**
     * 用户唯一标识
     */
    IDENTIFIER("identifier", "用户唯一标识"),
    /**
     * 登录类型
     */
    CHANNEL_TYPE("channelType", "登录类型"),
    /**
     * 用户 ID
     */
    USER_ID("userId", "用户 ID"),
    /**
     * 用户名
     */
    USERNAME("username", "用户名"),
    /**
     * 租户 ID
     */
    TENANT_ID("tenantId", "租户 ID"),
    /**
     * client id
     */
    APP_ID("appId", "client id"),
    /**
     * user details
     */
    USER_DETAILS("userDetails", "userDetails"),
    /**
     * 用户权限
     */
    AUTHORITIES("authorities", "用户权限"),
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