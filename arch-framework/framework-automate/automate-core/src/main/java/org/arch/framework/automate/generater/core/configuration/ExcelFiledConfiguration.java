package org.arch.framework.automate.generater.core.configuration;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.arch.framework.automate.generater.core.SchemaConfiguration;

@Data
public class ExcelFiledConfiguration implements SchemaConfiguration {
    private JSONObject header = new JSONObject();
}
