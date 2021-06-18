package org.arch.automate.reader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.automate.configuration.DdlConfiguration;
import org.arch.automate.enums.SchemaPattern;
import org.arch.automate.enums.SchemaType;
import org.arch.automate.core.*;
import org.arch.automate.properties.SchemaProperties;
import org.arch.automate.reader.xmind.utils.XmindUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/26/2021 11:06 AM
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DdlSchemaReader extends AbstractSchemaReader<DdlConfiguration> implements SchemaReadable {

    @Override
    public SchemaType getTyp() {
        return SchemaType.DDL;
    }

    @Override
    public List<SchemaData> read(SchemaProperties schemaProperties) {
        // 如果有有特殊处理，再次处理，如果没有则调用父类通用处理
        return super.doRead(schemaProperties);
    }

    @Override
    protected List<? extends SchemaData> readMvc(ReaderConfiguration<DdlConfiguration> readerConfiguration) {
        List<DatabaseSchemaData> databaseSchemaDatas = new ArrayList<>();
        XmindUtils.getProject(readerConfiguration).getModules().forEach(module -> {
            module.getDatabases().forEach(db -> {
                DatabaseSchemaData databaseSchemaData = new DatabaseSchemaData();
                databaseSchemaData.setDatabase(db);
                databaseSchemaData.setSchemaPattern(SchemaPattern.MVC);
                databaseSchemaDatas.add(databaseSchemaData);
            });
        });
        return databaseSchemaDatas;
    }


    @Override
    protected List<? extends SchemaData> readApi(ReaderConfiguration<DdlConfiguration> readerConfiguration) {
        List<ApiSchemaData> apiSchemaDatas = new ArrayList<>();
        XmindUtils.getProject(readerConfiguration).getModules().forEach(module -> {
            module.getApis().forEach(api -> {
                ApiSchemaData apiSchemaData = new ApiSchemaData();
                apiSchemaData.setApi(api);
                apiSchemaData.setSchemaPattern(SchemaPattern.API);
                apiSchemaDatas.add(apiSchemaData);
            });
        });
        return apiSchemaDatas;
    }

    @Override
    protected ReaderConfiguration<DdlConfiguration> buildConvertConfiguration(String resName, SchemaProperties schemaProperties, SchemaPattern schemaPattern) {
        ReaderConfiguration<DdlConfiguration> readerConfiguration = new ReaderConfiguration<>();
        readerConfiguration.setConfiguration((DdlConfiguration) schemaProperties.getSchemaConfiguration());
        readerConfiguration.setResource(resName);
        readerConfiguration.setPattern(schemaPattern);
        return readerConfiguration;
    }

}