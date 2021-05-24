package org.arch.framework.automate.generater.reader;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.arch.framework.automate.common.configuration.DatabaseConfiguration;
import org.arch.framework.automate.common.database.Database;
import org.arch.framework.automate.common.database.Table;
import org.arch.framework.automate.from.service.DatabaseService;
import org.arch.framework.automate.generater.core.*;
import org.arch.framework.automate.generater.properties.SchemaProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/26/2021 11:05 AM
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseSchemaReader extends AbstractSchemaReader<DatabaseConfiguration> implements SchemaReadable {

    private final DatabaseService databaseService;

    @Override
    public SchemaType getTyp() {
        return SchemaType.DATABASE;
    }

    @Override
    public List<SchemaData> read(SchemaProperties schemaProperties) {
        return super.doRead(schemaProperties);
    }

    @Override
    protected List<? extends SchemaData> readMvc(ReaderConfiguration<DatabaseConfiguration> readerConfiguration) {
        List<DatabaseSchemaData> databaseSchemaDatas = new ArrayList<>();
        // 获取数据库配置
        DatabaseConfiguration databaseConfiguration = readerConfiguration.getConfiguration();
        Database database = new Database();
        database.setName(databaseConfiguration.getDatabase());
        // 获取数据库的table
        List<Table> tables = databaseService.getDatabaseTablesInfo(databaseConfiguration, readerConfiguration.getResource());
        database.getTables().addAll(tables);
        DatabaseSchemaData databaseSchemaData = new DatabaseSchemaData();
        databaseSchemaData.setDatabase(database);
        databaseSchemaData.setSchemaPattern(SchemaPattern.MVC);
        databaseSchemaDatas.add(databaseSchemaData);
        return CollectionUtils.isEmpty(tables) ? Lists.newArrayList() : databaseSchemaDatas;
    }

    @Override
    protected List<? extends SchemaData> readApi(ReaderConfiguration<DatabaseConfiguration> readerConfiguration) {
        List<ApiSchemaData> apiSchemaData = new ArrayList<>();

        return apiSchemaData;
    }

    @Override
    protected ReaderConfiguration<DatabaseConfiguration> buildConvertConfiguration(String resName, SchemaProperties schemaProperties, SchemaPattern schemaPattern) {
        ReaderConfiguration<DatabaseConfiguration> readerConfiguration = new ReaderConfiguration<>();
        readerConfiguration.setConfiguration((DatabaseConfiguration) schemaProperties.getSchemaConfiguration());
        readerConfiguration.setResource(resName);
        readerConfiguration.setPattern(schemaPattern);
        return readerConfiguration;
    }
}
