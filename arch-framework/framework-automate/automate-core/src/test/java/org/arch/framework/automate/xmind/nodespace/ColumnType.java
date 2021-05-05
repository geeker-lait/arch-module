package org.arch.framework.automate.xmind.nodespace;

import org.springframework.lang.NonNull;

/**
 * database column type
 *
 * @author YongWu zheng
 * @version V2.0  Created by 2021-04-28 15:05
 */
public enum ColumnType {

    BIGINT("bigint", "19", "Long"),
    LONG("bigint", "19", "Long"),
    INTEGER("int", "11", "Integer"),
    INT("int", "11", "Integer"),
    CHAR("char", "64", "String"),
    VARCHAR("varchar", "64", "String"),
    STRING("varchar", "64", "String"),
    TINYTEXT("tinytext", "255", "String"),
    TEXT("text", "65535", "String"),
    MEDIUMTEXT("mediumtext", "16777215", "String"),
    LONGTEXT("longtext", "4294967295", "String"),
    BIT("bit", "1", "Boolean"),
    BINARY("binary", "", "byte[]"),
    VARBINARY("varbinary", "", "byte[]"),
    BLOB("blob", "", "byte[]"),
    MEDIUMBLOB("mediumblob", "", "byte[]"),
    LONGBLOB("longblob", "", "byte[]"),
    BOOLEAN("tinyint", "1", "Boolean"),
    TINYINT("tinyint", "4", "Integer"),
    DECIMAL("decimal", "11,2", "java.math.BigDecimal"),
    FLOAT("decimal", "11,2", "java.math.BigDecimal"),
    DOUBLE("decimal", "11,2", "java.math.BigDecimal"),
    TIMESTAMP("timestamp", "14", "java.time.LocalDateTime"),
    DATETIME("datetime", "", "java.time.LocalDateTime"),
    TIME("time", "", "java.time.LocalDateTime"),
    DATE("datetime", "", "java.time.LocalDateTime");
    /**
     * 默认字段类型长度字符串
     */
    private final String defValue;
    /**
     * column 类型
     */
    private final String type;
    /**
     * java 类型
     */
    private final String javaType;

    ColumnType(String type, String defValue, String javaType) {
        this.type = type;
        this.defValue = defValue;
        this.javaType = javaType;
    }

    public String getDefValue() {
        return defValue;
    }

    public String getType() {
        return type;
    }

    public String getJavaType() {
        return javaType;
    }

    public static boolean isString(@NonNull String columnType) {
        return VARCHAR.name().equalsIgnoreCase(columnType) ||
               CHAR.name().equalsIgnoreCase(columnType) ||
               STRING.name().equalsIgnoreCase(columnType) ||
               TINYTEXT.name().equalsIgnoreCase(columnType) ||
               TEXT.name().equalsIgnoreCase(columnType) ||
               MEDIUMTEXT.name().equalsIgnoreCase(columnType) ||
               LONGTEXT.name().equalsIgnoreCase(columnType);
    }

    public static boolean isArray(@NonNull String columnType) {
        return BLOB.name().equalsIgnoreCase(columnType) ||
               MEDIUMBLOB.name().equalsIgnoreCase(columnType) ||
               BINARY.name().equalsIgnoreCase(columnType) ||
               VARBINARY.name().equalsIgnoreCase(columnType) ||
               LONGBLOB.name().equalsIgnoreCase(columnType);
    }

}