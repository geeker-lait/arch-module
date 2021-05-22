package org.arch.framework.automate.generater.reader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.core.ReaderConfiguration;
import org.arch.framework.automate.generater.core.SchemaData;
import org.arch.framework.automate.generater.core.SchemaPattern;
import org.arch.framework.automate.generater.core.SchemaReadable;
import org.arch.framework.automate.generater.core.SchemaType;
import org.arch.framework.automate.common.database.Database;
import org.arch.framework.automate.generater.core.*;
import org.arch.framework.automate.generater.core.configuration.DatabaseConfiguration;
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

    //private final DatabaseSchemaService databaseSchemaService;

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

        // 动态链接数据库

        // 获取数据库的table
        //List<TableProperties> tableProperties = databaseService.getDatabaseTablesInfo(params.getDatabaseProperties(), params.getDatabaseName());
        //return CollectionUtils.isEmpty(tableProperties) ? Lists.newArrayList() : tableProperties;
        // 获取数据库的table
        //List<TableProperties> tableProperties = databaseSchemaService.getTableProperties().apply(new DatabaseTablePropertiesParam(databaseProperties, res));
        //databaseProperties.setName(res);
        //databaseProperties.setTables(tableProperties);
        //databaseProperties.setPattern(SchemaPattern.MVC.getPattern());
        return databaseSchemaDatas;
    }

    @Override
    protected List<? extends SchemaData> readApi(ReaderConfiguration<DatabaseConfiguration> readerConfiguration) {
        List<MethodSchemaData> methodSchemaDatas = new ArrayList<>();

        return methodSchemaDatas;
    }

    @Override
    protected ReaderConfiguration<DatabaseConfiguration> buildConvertConfiguration(String resName, SchemaProperties schemaProperties, SchemaPattern schemaPattern) {
        ReaderConfiguration<DatabaseConfiguration> readerConfiguration = new ReaderConfiguration<>();
        readerConfiguration.setConfiguration((DatabaseConfiguration) schemaProperties.getSchemaConfiguration());
        readerConfiguration.setResource(resName);
        readerConfiguration.setPattern(schemaPattern);
        return readerConfiguration;
    }
//
//    @Override
//    protected List<SchemaPatternable> readMvc(String res, Map<String, String> configuration) {
//
//        // 获取数据库的table
////            List<TableProperties> tableProperties = databaseService.getDatabaseTablesInfo(params.getDatabaseProperties(), params.getDatabaseName());
////            return CollectionUtils.isEmpty(tableProperties) ? Lists.newArrayList() : tableProperties;
//        DatabaseProperties databaseProperties = BeanUtil.toBean(configuration, DatabaseProperties.class);
//        // 获取数据库的table
//        List<TableProperties> tableProperties = databaseSchemaService.getTableProperties().apply(new DatabaseTablePropertiesParam(databaseProperties, res));
//        databaseProperties.setName(res);
//        databaseProperties.setTables(tableProperties);
//        databaseProperties.setPattern(SchemaPattern.MVC.getPattern());
//        return Arrays.asList(databaseProperties);
//    }
//
//    @Override
//    protected List<SchemaPatternable> readApi(String res, Map<String, String> configuration) {
//        DatabaseProperties databaseProperties = BeanUtil.toBean(configuration, DatabaseProperties.class);
//        // todo getApiProperties.apply 入参需要构建，暂时只用 res(为了不报错)   getApiProperties暂时是空实现
//        List<MethodProperties> tableProperties = databaseSchemaService.getApiProperties().apply(res);
//        databaseProperties.setName(res);
//        databaseProperties.setApis(tableProperties);
//        databaseProperties.setPattern(SchemaPattern.API.getPattern());
//
//        return Arrays.asList(databaseProperties);
//    }


}
