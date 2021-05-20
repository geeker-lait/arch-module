package org.arch.framework.automate.generater.core;

import lombok.Data;
import org.arch.framework.automate.common.api.Interfac;
import org.arch.framework.automate.common.database.Database;

@Data
public class DatabaseSchemaData implements SchemaData {
    private Database database;
    // 匹配模式
    private SchemaPattern schemaPattern = SchemaPattern.MVC;

    @Override
    public Interfac getInterfac() {
        return null;
    }
}
