package org.arch.framework.automate.generater.core;

import lombok.Data;
import org.arch.framework.automate.common.api.Interfac;
import org.arch.framework.automate.common.database.Database;

@Data
public class MethodSchemaData implements SchemaData {
    private Interfac interfac;
    // 匹配模式
    private SchemaPattern schemaPattern = SchemaPattern.API;

    @Override
    public Database getDatabase() {
        return null;
    }
}
