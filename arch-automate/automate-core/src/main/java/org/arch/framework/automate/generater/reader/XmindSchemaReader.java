package org.arch.framework.automate.generater.reader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.core.*;
import org.arch.framework.automate.common.configuration.XmindConfiguration;
import org.arch.framework.automate.generater.properties.SchemaProperties;
import org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils;
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
public class XmindSchemaReader extends AbstractSchemaReader<XmindConfiguration> implements SchemaReadable {

    @Override
    public SchemaType getTyp() {
        return SchemaType.XMIND;
    }

    @Override
    public List<SchemaData> read(SchemaProperties schemaProperties) {
        // 如果有有特殊处理，再次处理，如果没有则调用父类通用处理
        return super.doRead(schemaProperties);
    }

    @Override
    protected List<? extends SchemaData> readMvc(ReaderConfiguration<XmindConfiguration> readerConfiguration) {
        List<DatabaseSchemaData> databaseSchemaDatas = new ArrayList<>();
        XmindUtils.getProject(readerConfiguration).getModules().forEach(module -> {
            module.getDatabases().forEach(db -> {
                DatabaseSchemaData databaseSchemaData = new DatabaseSchemaData();
                databaseSchemaData.setDatabase(db);
                databaseSchemaData.setSchemaPattern(SchemaPattern.MVC);
                databaseSchemaData.setIdentifier("" + module.hashCode());
                databaseSchemaDatas.add(databaseSchemaData);
            });
        });
        return databaseSchemaDatas;
    }


    @Override
    protected List<? extends SchemaData> readApi(ReaderConfiguration<XmindConfiguration> readerConfiguration) {
        List<ApiSchemaData> apiSchemaDatas = new ArrayList<>();
        XmindUtils.getProject(readerConfiguration).getModules().forEach(module -> {
            module.getApis().forEach(api -> {
                ApiSchemaData apiSchemaData = new ApiSchemaData();
                apiSchemaData.setApi(api);
                apiSchemaData.setSchemaPattern(SchemaPattern.API);
                apiSchemaData.setIdentifier("" + module.hashCode());
                apiSchemaDatas.add(apiSchemaData);
            });
        });
        return apiSchemaDatas;
    }

    @Override
    protected ReaderConfiguration<XmindConfiguration> buildConvertConfiguration(String resName, SchemaProperties schemaProperties, SchemaPattern schemaPattern) {
        ReaderConfiguration<XmindConfiguration> readerConfiguration = new ReaderConfiguration<>();
        readerConfiguration.setConfiguration((XmindConfiguration) schemaProperties.getSchemaConfiguration());
        readerConfiguration.setResource(resName);
        readerConfiguration.setPattern(schemaPattern);
        return readerConfiguration;
    }

}
