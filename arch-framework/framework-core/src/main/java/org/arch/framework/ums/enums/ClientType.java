package org.arch.framework.ums.enums;

/**
 * client 类型
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.14 17:40
 */
public enum ClientType implements DictionaryItemInfo {
    /**
     * 安卓
     */
    ANDROID("安卓", ""),
    /**
     * 苹果系统
     */
    IOS("苹果系统", ""),
    /**
     * 小程序
     */
    MINI("小程序", ""),
    /**
     * 资源服务器
     */
    SERVER("资源服务器", ""),
    /**
     * 第三方
     */
    THIRD_PART("第三方", ""),
    /**
     * 网页
     */
    WEB("网页", "");

    /**
     * title
     */
    private final String title;
    /**
     * 备注
     */
    private final String mark;

    ClientType(String title, String mark) {
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
