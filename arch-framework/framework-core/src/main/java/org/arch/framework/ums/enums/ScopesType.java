package org.arch.framework.ums.enums;

/**
 * scopes 类型, 用于 {@code AccountOauthClient#scopes} 字段
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.13 20:21
 */
public enum ScopesType implements DictionaryItemInfo {
    /**
     * 访问 jws set uri 需此访问权限标识
     */
    JWK("需此访问权限标识", ""),
    /**
     * 访问认证服务模块的 服务权限
     */
    SSO("访问认证服务模块的服务权限", ""),
    /**
     * 访问统一认证系统的 服务权限
     */
    OAUTH2("访问统一认证系统的服务权限", ""),
    /**
     * 访问用户管理模块的 所有 服务权限
     */
    UMS("访问用户管理模块的所有服务权限", ""),
    /**
     * 访问用户管理模块的 账号 服务权限
     */
    ACCOUNT("访问用户管理模块的账号账号服务权限", ""),
    /**
     * 访问用户管理模块的 用户 服务权限
     */
    USER("访问用户管理模块的用户服务权限", ""),
    /**
     * 访问用户管理模块的 会员 服务权限
     */
    MEMBER("访问用户管理模块的会员服务权限", ""),
    /**
     * 访问用户管理模块的 商户 服务权限
     */
    MERCHANT("访问用户管理模块的商户服务权限", ""),
    /**
     * 访问商品管理系统的 服务权限
     */
    PMS("访问商品管理系统的服务权限", ""),
    /**
     * 访问订单管理系统的 服务权限
     */
    OMS("访问订单管理系统的服务权限", ""),
    /**
     * 访问对象存储系统的 服务权限
     */
    OSS("访问对象存储系统的服务权限", ""),
    /**
     * 访问看门狗管理系统的 服务权限
     */
    WATCHDOG("访问看门狗管理系统的服务权限", ""),
    /**
     * 访问看营销管理系统的 服务权限
     */
    MARKETING("访问看营销管理系统的服务权限", ""),
    /**
     * 访问支付系统的 支付 服务权限
     */
    PAY("访问支付系统的支付服务权限", "");

    /**
     * title
     */
    private final String title;
    /**
     * 备注
     */
    private final String mark;

    ScopesType(String title, String mark) {
        this.title = title;
        this.mark = mark;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getVal() {
        return this.name().toLowerCase();
    }

    @Override
    public int getSeq() {
        return this.ordinal();
    }

    @Override
    public String getMark() {
        return this.mark;
    }

}
