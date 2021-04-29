package org.arch.framework.automate.xmind.nodespace;


/**
 * Prams type
 *
 * @author YongWu zheng
 * @version V2.0  Created by 2021-04-28 15:04
 */
public enum ParamType {
    /**
     * 实体类
     */
    ENTITY("", ""),
    /**
     * 注释字段
     */
    ANNOT("", ""),
    /**
     * 注释对象
     */
    ANNOT_E("", ""),
    /**
     * 注释键值对
     */
    ANNOT_VAL("", ""),
    /**
     * API 链接
     */
    URI("", ""),
    /**
     * 包路径
     */
    PKG("", ""),
    ARRAY("", ""),
    LIST("List", "java.util.List"),
    SET("Set", "java.util.Set"),
    MAP("Map", "java.util.Map"),
    COLLECTION("Collection", "java.util.Collection"),
    LONG("Long", ""),
    STRING("String", ""),
    INT("Integer", ""),
    INTEGER("Integer", ""),
    VOID("void", ""),
    BOOLEAN("Boolean", ""),
    FLOAT("Float", ""),
    DOUBLE("Double", ""),
    BYTE("Byte", ""),
    SHORT("Short", ""),
    BIG_DECIMAL("BigDecimal", "java.math.BigDecimal"),
    DATE("Date", "java.util.Date");

    /**
     * 类型
     */
    private final String type;
    private final String pkg;

    ParamType(String type, String pkg) {
        this.type = type;
        this.pkg = pkg;
    }

    public String getType() {
        return type;
    }

    public String getPkg() {
        return pkg;
    }
}