package org.arch.framework.ums.enums;

/**
 * 地址类型
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.29 20:21
 */
public enum AddressType implements DictionaryItemInfo {
    /**
     * 工作地址
     */
    WORK("工作地址", ""),
    /**
     * 家庭地址
     */
    HOME("家庭地址", ""),
    /**
     * 收货地址
     */
    RECEIVE("收货地址", "");

    /**
     * title
     */
    private final String title;
    /**
     * 备注
     */
    private final String mark;

    AddressType(String title, String mark) {
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
