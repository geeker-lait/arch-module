package org.arch.framework.automate.xmind.nodespace;

import org.springframework.lang.NonNull;

/**
 * database column type
 *
 * @author YongWu zheng
 * @version V2.0  Created by 2021-04-28 15:05
 */
public enum ColumnType {
    BIGINT("bigint", "19"),
    LONG("bigint", "19"),
    INTEGER("int", "11"),
    INT("int", "11"),
    CHAR("char", "50"),
    VARCHAR("varchar", "50"),
    STRING("varchar", "50"),
    TINYTEXT("tinytext", "255"),
    TEXT("text", "65535"),
    MEDIUMTEXT("mediumtext", "16777215"),
    LONGTEXT("longtext", "4294967295"),
    BIT("bit", "1"),
    BINARY("binary", ""),
    BLOB("blob", ""),
    MEDIUMBLOB("mediumblob", ""),
    LONGBLOB("longblob", ""),
    BOOLEAN("tinyint", "1"),
    TINYINT("tinyint", "4"),
    DECIMAL("decimal", "11,2"),
    FLOAT("decimal", "11,2"),
    DOUBLE("decimal", "11,2"),
    TIMESTAMP("timestamp", "14"),
    DATETIME("datetime", ""),
    TIME("time", ""),
    DATE("datetime", "");
    /**
     * 默认字段类型长度字符串
     */
    private final String defValue;
    /**
     * column 类型
     */
    private final String type;

    ColumnType(String type, String defValue) {
        this.type = type;
        this.defValue = defValue;
    }

    public String getDefValue() {
        return defValue;
    }

    public String getType() {
        return type;
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
}