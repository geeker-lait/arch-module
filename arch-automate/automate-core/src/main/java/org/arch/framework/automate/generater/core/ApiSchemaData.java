package org.arch.framework.automate.generater.core;

import lombok.Data;
import org.arch.automate.common.api.Api;
import org.arch.automate.common.database.Database;
import org.arch.automate.common.enums.SchemaPattern;
import org.arch.automate.common.Module;

@Data
public class ApiSchemaData implements SchemaData {
    private Api api;
    // 匹配模式
    private SchemaPattern schemaPattern = SchemaPattern.API;
    /**
     * 生成 {@link SchemaData} 的源 hash 值, 方便相同源时, 生成 MVC 或 API 时相互关联.
     * 源如: {@link Module}, {@link ReaderConfiguration}, 具体参考 {@link SchemaReadable} 实现类.
     */
    private String identifier;

    @Override
    public Database getDatabase() {
        return null;
    }

}
