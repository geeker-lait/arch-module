package org.arch.framework.automate.generater.properties;

import lombok.Builder;
import lombok.Data;
import org.arch.framework.automate.generater.core.SchemaMetadata;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Data
@Builder
public final class ExcelProperties implements SchemaMetadata {
    private String sheetName;
    private Integer row;
    private String table;
    private String column;
    private String type;
    private String length;
    private String isnull;
    private String defaultValue;
    private String primaryKey;
    private String unique;
    private String forienKey;
    private String comment;
    // 匹配模式
    private String pattern;
    @NestedConfigurationProperty
    private List<TableProperties> tables = new ArrayList<>();
    @NestedConfigurationProperty
    private List<MethodProperties> apis = new ArrayList<>();
    @Override
    public String getSchemaName() {
        return sheetName;
    }
}
