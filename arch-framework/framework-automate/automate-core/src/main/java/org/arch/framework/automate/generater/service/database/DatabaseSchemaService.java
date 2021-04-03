package org.arch.framework.automate.generater.service.database;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.arch.framework.automate.from.service.DatabaseService;
import org.arch.framework.automate.generater.core.AbstractSchemaService;
import org.arch.framework.automate.generater.core.SchemaMetadata;
import org.arch.framework.automate.generater.properties.MethodProperties;
import org.arch.framework.automate.generater.properties.TableProperties;
import org.arch.framework.automate.generater.service.SchemaService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 3/24/2021 10:06 AM
 */
@Component
@AllArgsConstructor
public class DatabaseSchemaService extends AbstractSchemaService implements SchemaService {

    private final DatabaseService databaseService;

    @Override
    public Function<DatabaseTablePropertiesParam, List<TableProperties>> getTableProperties() {
        return params -> {
            // 获取数据库的table
            List<TableProperties> tableProperties = databaseService.getDatabaseTablesInfo(params.getDatabaseProperties(), params.getDatabaseName());
            return CollectionUtils.isEmpty(tableProperties) ? Lists.newArrayList() : tableProperties;
        };

    }


    @Override
    public <T> Function<T, List<MethodProperties>> getApiProperties() {
        return null;
    }

    @Override
    public <T> Function<T, List<SchemaMetadata>>  getSchemaMatedatas() {
        return null;
    }
}
