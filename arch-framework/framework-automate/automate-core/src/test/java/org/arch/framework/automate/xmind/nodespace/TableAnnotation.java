package org.arch.framework.automate.xmind.nodespace;

/**
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.4.28 22:46
 */
public enum TableAnnotation {

    TABLE_ID("TableId"),
    TABLE_FIELD("TableField"),
    TABLE_NAME("TableName"),
    TABLE_LOGIC("TableLogic"),
    VERSION("Version");

    private final String annotName;

    TableAnnotation(String annotName) {
        this.annotName = annotName;
    }

    public String getAnnotName() {
        return annotName;
    }
}
