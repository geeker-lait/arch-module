package org.arch.automate.core;

import lombok.Data;
import org.arch.framework.beans.schema.api.Api;
import org.arch.framework.beans.schema.database.Database;
import org.arch.automate.enums.SchemaPattern;
import org.arch.automate.Module;

@Data
public class DatabaseSchemaData implements SchemaData {
    private Database database;
    // 匹配模式
    private SchemaPattern schemaPattern = SchemaPattern.MVC;
    /**
     * 生成 {@link SchemaData} 的源 hash 值, 方便相同源时, 生成 MVC 或 API 时相互关联.
     * 源如: {@link Module}, {@link ReaderConfiguration}, 具体参考 {@link SchemaReadable} 实现类.
     */
    private String identifier;

    @Override
    public Api getApi() {
        return null;
    }
}
