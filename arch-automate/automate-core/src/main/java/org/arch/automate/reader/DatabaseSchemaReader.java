package org.arch.automate.reader;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.arch.automate.configuration.DatabaseConfiguration;
import org.arch.automate.core.*;
import org.arch.automate.enums.SchemaPattern;
import org.arch.automate.enums.SchemaType;
import org.arch.automate.properties.SchemaProperties;
import org.arch.form.core.DatabaseService;
import org.arch.form.core.properties.DatabaseProperties;
import org.arch.framework.beans.schema.database.Database;
import org.arch.framework.beans.schema.database.Table;
import org.springframework.beans.BeanUtils;
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

        DatabaseProperties databaseProperties = new DatabaseProperties();
        BeanUtils.copyProperties(databaseConfiguration, databaseProperties);
        // 获取数据库的table
        List<Table> tables = databaseService.getDatabaseTablesInfo(databaseProperties, readerConfiguration.getResource());
        database.getTables().addAll(tables);
        DatabaseSchemaData databaseSchemaData = new DatabaseSchemaData();
        databaseSchemaData.setDatabase(database);
        databaseSchemaData.setSchemaPattern(SchemaPattern.MVC);
        databaseSchemaData.setIdentifier("" + readerConfiguration.hashCode());
        databaseSchemaDatas.add(databaseSchemaData);
        return CollectionUtils.isEmpty(tables) ? Lists.newArrayList() : databaseSchemaDatas;
    }

    @Override
    protected List<? extends SchemaData> readApi(ReaderConfiguration<DatabaseConfiguration> readerConfiguration) {
        return new ArrayList<ApiSchemaData>();
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
