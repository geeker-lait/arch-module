package org.arch.framework.ums.enums;

/**
 * 亲朋类型
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.29 22:57
 */
public enum RelativesType implements DictionaryItemInfo {
    /**
     * 家属
     */
    FAMILY("家属", ""),
    /**
     * 朋友
     */
    FRIEND("朋友", "");

    /**
     * title
     */
    private final String title;
    /**
     * 备注
     */
    private final String mark;

    RelativesType(String title, String mark) {
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
