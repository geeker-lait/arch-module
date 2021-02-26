package org.arch.framework.automate.common.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/26/2021 6:50 PM
 */
public class JdbcTypeUtils {
    private static final Map<String, Class> sqlFieldTypeMapping = new HashMap<>();

    static {
        sqlFieldTypeMapping.put("VARCHAR", String.class);
        sqlFieldTypeMapping.put("CHAR", String.class);
        sqlFieldTypeMapping.put("TEXT", String.class);
        sqlFieldTypeMapping.put("MEDIUMTEXT", String.class);
        sqlFieldTypeMapping.put("LONGTEXT", String.class);
        sqlFieldTypeMapping.put("TINYTEXT", String.class);
        sqlFieldTypeMapping.put("BIT", Boolean.class);
        sqlFieldTypeMapping.put("INT", Integer.class);
        sqlFieldTypeMapping.put("BIGINT", Long.class);
        sqlFieldTypeMapping.put("DOUBLE", Double.class);
        sqlFieldTypeMapping.put("TINYINT", Integer.class);
        sqlFieldTypeMapping.put("FLOAT", Float.class);
        sqlFieldTypeMapping.put("DECIMAL", BigDecimal.class);
        sqlFieldTypeMapping.put("INT UNSIGNED", Integer.class);
        sqlFieldTypeMapping.put("BIGINT UNSIGNED", Integer.class);
        sqlFieldTypeMapping.put("DECIMAL UNSIGNED", BigDecimal.class);
        sqlFieldTypeMapping.put("DATETIME", Date.class);
        sqlFieldTypeMapping.put("TIME", Date.class);
        sqlFieldTypeMapping.put("DATE", Date.class);
        sqlFieldTypeMapping.put("TIMESTAMP", Date.class);
        sqlFieldTypeMapping.put("BOOLEAN", Boolean.class);
    }

    public static Class getFieldType(String columnType) {
        Class aClass = sqlFieldTypeMapping.get(columnType);
        if (aClass == null) {
            return sqlFieldTypeMapping.get(columnType.toUpperCase());
        }
        return aClass;
    }
}
