package org.arch.automate.reader.xmind.nodespace;

import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * database column type
 *
 * @author YongWu zheng
 * @version V2.0  Created by 2021-04-28 15:05
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
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
    private final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

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

    public static boolean isInteger(@NonNull String columnType) {
        return BIGINT.name().equalsIgnoreCase(columnType) ||
                LONG.name().equalsIgnoreCase(columnType) ||
                INT.name().equalsIgnoreCase(columnType) ||
                INTEGER.name().equalsIgnoreCase(columnType);
    }

    public static boolean isArray(@NonNull String columnType) {
        return BLOB.name().equalsIgnoreCase(columnType) ||
                MEDIUMBLOB.name().equalsIgnoreCase(columnType) ||
                BINARY.name().equalsIgnoreCase(columnType) ||
                VARBINARY.name().equalsIgnoreCase(columnType) ||
                LONGBLOB.name().equalsIgnoreCase(columnType);
    }

    public static Object convert(String type, String val) {
        if (val == null || val.isEmpty()) {
            return null;
        }
        if (type.contains(BIGINT.getType())) {
            return Long.valueOf(val);
        } else if (type.contains(INT.getType())) {
            return Integer.valueOf(val);
        } else if (type.contains(DATETIME.getType())) {
            try {
                return LocalDateTime.parse(val, DTF).toInstant(ZoneOffset.of("+8")).toEpochMilli();
            } catch (Exception e) {
                return LocalDateTime.parse(val).toInstant(ZoneOffset.of("+8")).toEpochMilli();
            }
        } else if (type.contains(VARCHAR.getType())) {
            return val;
        }
        return null;
    }

}