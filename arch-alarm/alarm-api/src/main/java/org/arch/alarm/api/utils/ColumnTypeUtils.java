package org.arch.alarm.api.utils;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author lait.zhang@gmail.com
 * @description: database column type
 * @weixin PN15855012581
 * @date 5/6/2021 9:15 PM
 */
public enum ColumnTypeUtils {

    BIGINT("bigint", "19", "java.lang.Long"),
    LONG("bigint", "19", "java.lang.Long"),
    INTEGER("int", "11", "java.lang.Integer"),
    INT("int", "11", "java.lang.Integer"),
    CHAR("char", "64", "java.lang.String"),
    VARCHAR("varchar", "64", "java.lang.String"),
    STRING("varchar", "64", "java.lang.String"),
    TINYTEXT("tinytext", "255", "java.lang.String"),
    TEXT("text", "65535", "java.lang.String"),
    MEDIUMTEXT("mediumtext", "16777215", "java.lang.String"),
    LONGTEXT("longtext", "4294967295", "java.lang.String"),
    BIT("bit", "1", "java.lang.Boolean"),
    BINARY("binary", "", "byte[]"),
    VARBINARY("varbinary", "", "byte[]"),
    BLOB("blob", "", "byte[]"),
    MEDIUMBLOB("mediumblob", "", "byte[]"),
    LONGBLOB("longblob", "", "byte[]"),
    BOOLEAN("tinyint", "1", "java.lang.Boolean"),
    TINYINT("tinyint", "4", "java.lang.Integer"),
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
    //private final static DateTimeFormatter FMT24 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");


    ColumnTypeUtils(String type, String defValue, String javaType) {
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

    public static boolean isString(String columnType) {
        return VARCHAR.name().equalsIgnoreCase(columnType) ||
                CHAR.name().equalsIgnoreCase(columnType) ||
                STRING.name().equalsIgnoreCase(columnType) ||
                TINYTEXT.name().equalsIgnoreCase(columnType) ||
                TEXT.name().equalsIgnoreCase(columnType) ||
                MEDIUMTEXT.name().equalsIgnoreCase(columnType) ||
                LONGTEXT.name().equalsIgnoreCase(columnType);
    }

    public static boolean isArray(String columnType) {
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


    public static void main(String[] args) throws ParseException {

        String time = "2021-05-06 18:51:31.000001";
        LocalDateTime date = LocalDateTime.parse(time, DTF);
        Long dt = date.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println(dt);
        LocalDateTime.ofInstant(Instant.ofEpochMilli(dt), ZoneOffset.of("+8"));


        long t1 = LocalDateTime.of(2021, 5, 6, 17, 27).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long t2 = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long t3 = t2 - t1;
        System.out.println("t2 = " + t2);
        System.out.println("t1 = " + t1);
        System.out.println("t3 = " + t3);
        // 分钟
        long t4 = t3 / 1000 / 60;
        System.out.println(t4);


        System.out.println(LocalDateTime.parse(time, DTF));
        System.out.println(convert(ColumnTypeUtils.DATETIME.getType(), time));
        System.out.println(System.currentTimeMillis());
        System.out.println(((LocalDateTime) convert(ColumnTypeUtils.DATETIME.getType(), time)).toInstant(ZoneOffset.of("+8")).toEpochMilli());
    }

}
