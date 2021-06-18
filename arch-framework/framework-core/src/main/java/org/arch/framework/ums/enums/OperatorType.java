package org.arch.framework.ums.enums;

/**
 * 操作类型
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.29 22:54
 */
public enum OperatorType implements DictionaryItemInfo {
    /**
     * 查询
     */
    QUERY("查询", ""),
    /**
     * 更新
     */
    UPDATE("更新", ""),
    /**
     * 新增
     */
    ADD("新增", ""),
    /**
     * 删除
     */
    DELETE("删除", "");

    /**
     * title
     */
    private final String title;
    /**
     * 备注
     */
    private final String mark;

    OperatorType(String title, String mark) {
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
