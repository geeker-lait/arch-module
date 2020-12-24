package org.arch.framework.generater.metadata;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 字段信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FieldInfo extends BaseClassInfo {

    /**
     * 字段名称
     */
    private String name;

    private String length;

    private String type;

    /**
     * 字段上的注解
     */
    private List<AnnotationInfo> annotations;

    /**
     * 字段在数据库的注释
     */
    private String comment;

    /**
     * 字段在数据库的字段名
     */
    private String columnName;

}
