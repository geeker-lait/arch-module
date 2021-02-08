package org.arch.framework.automate.common.metadata;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 字段信息
 * {
 * "name": "confirmed_at",
 * "type": "DATETIME",
 * "not_null": false,
 * "comment": "User confirmation date"
 * }
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FieldInfo extends BaseClassInfo {

    /**
     * 字段名称
     */
    private String name;

    /**
     * 长度
     */
    private String length;

    /**
     * 类型
     */
    private String type;


    /**
     * 字段在数据库的注释
     */
    private String comment;

    /**
     * 字段在数据库的字段名
     */
    private String columnName;

    /**
     * 是否为空
     */
    private boolean notNull;
}
