package org.arch.framework.automate.generater.core;

/**
 * @author lait.zhang@gmail.com
 * @description: api/mvc/或其他数据结构  标记用
 * @weixin PN15855012581
 * @date :
 */
public interface SchemaMetadata extends MethodSchemaMetadata, TableSchemaMetadata {
    String getSchemaName();
    void setPattern(String pattern);
    String getPattern();
}
