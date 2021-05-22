package org.arch.framework.automate.generater.core;

import lombok.Data;
import org.arch.framework.automate.common.api.Api;
import org.arch.framework.automate.common.api.Interfac;
import org.arch.framework.automate.common.database.Database;

@Data
public class ApiSchemaData implements SchemaData {
    private Api api;
    // 匹配模式
    private SchemaPattern schemaPattern = SchemaPattern.API;

    @Override
    public Database getDatabase() {
        return null;
    }

}
