package org.arch.automate.core;

import org.arch.framework.beans.schema.api.Api;
import org.arch.framework.beans.schema.database.Database;
import org.arch.automate.enums.SchemaPattern;
import org.arch.automate.Module;

public interface SchemaData {
    Database getDatabase();
    Api getApi();
    SchemaPattern getSchemaPattern();

    /**
     * 生成 {@link SchemaData} 的源 hash 值, 方便相同源时, 生成 MVC 或 API 时相互关联.
     * 源如: {@link Module}, {@link ReaderConfiguration}, 具体参考 {@link SchemaReadable} 实现类.
     * @return  源 hash 值
     */
    String getIdentifier();
}
