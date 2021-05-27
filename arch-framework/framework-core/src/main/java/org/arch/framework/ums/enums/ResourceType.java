package org.arch.framework.ums.enums;

/**
 * 权限类型
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.29 17:41
 */
public enum ResourceType implements DictionaryItemInfo {
    /**
     * 目录
     */
    DIRECTORY("目录", ""),
    /**
     * 菜单
     */
    MENU("菜单", ""),
    /**
     * 按钮
     */
    BUTTON("按钮", ""),
    /**
     * 链接
     */
    LINK("链接", ""),
    /**
     * 文件
     */
    FILE("文件", "");

    /**
     * title
     */
    private final String title;
    /**
     * 备注
     */
    private final String mark;

    ResourceType(String title, String mark) {
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
