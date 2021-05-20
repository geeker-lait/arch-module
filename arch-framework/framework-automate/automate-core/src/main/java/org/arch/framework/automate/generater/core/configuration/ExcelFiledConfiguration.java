package org.arch.framework.automate.generater.core.configuration;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.arch.framework.automate.generater.core.SchemaConfiguration;

import java.util.HashMap;
import java.util.Map;

@Data
public class ExcelFiledConfiguration implements SchemaConfiguration {
    private Map<String,String> header = new HashMap<>();

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
}
