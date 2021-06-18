package org.arch.framework.ums.enums;

import org.springframework.lang.Nullable;

/**
 * 数据字典的 code
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.24 18:24
 */
public enum DictionaryType {
    /**
     * 文件存储类型
     */
    STORAGE_TYPE("文件存储类型", "", StorageType.values()),
    /**
     * 权限资源类型
     */
    RESOURCE_TYPE("权限资源类型", "", ResourceType.values()),
    /**
     * 操作类型
     */
    OPERATOR_TYPE("操作类型", "", OperatorType.values()),
    /**
     * 客户端类型
     */
    CLIENT_TYPE("客户端类型", "", ClientType.values()),
    /**
     * AuthClient 的 scope 类型
     */
    SCOPES_TYPE("AuthClient 的 scope 类型", "", ScopesType.values()),
    /**
     * 登录类型
     */
    LOGIN_TYPE("登录类型", "", LoginType.values()),
    /**
     * 优惠券类型
     */
    TICKET_TYPE("优惠券类型", "", TicketType.values()),
    /**
     * 银行卡类型
     */
    BANK_CARD_TYPE("银行卡类型", "", CardType.values()),
    /**
     * 用户关系
     */
    USER_RELATIVES("用户关系", "", ResourceType.values()),
    /**
     * 会员权限类型
     */
    MEMBER_RIGHTS_TYPE("会员权限类型", "", null),
    /**
     * domain: 如 Https://www.baidu.com
     */
    DOMAIN("本应用域名", "domain: 如 Https://www.baidu.com", null);

    /**
     * 标题
     */
    private final String title;
    /**
     * 备注
     */
    private final String mark;
    /**
     * 备注
     */
    private final DictionaryItemInfo[] dictionaryItemInfos;

    DictionaryType(String title, String mark, DictionaryItemInfo[] dictionaryItemInfos) {
        this.title = title;
        this.mark = mark;
        this.dictionaryItemInfos = dictionaryItemInfos;
    }

    public String getTitle() {
        return title;
    }

    public String getMark() {
        return mark;
    }

    @Nullable
    public DictionaryItemInfo[] getDictionaryItemInfo() {
        return dictionaryItemInfos;
    }
}
