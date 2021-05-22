package org.arch.framework.automate.generater.reader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.common.module.Project;
import org.arch.framework.automate.generater.core.DatabaseSchemaData;
import org.arch.framework.automate.generater.core.MethodSchemaData;
import org.arch.framework.automate.generater.core.ReaderConfiguration;
import org.arch.framework.automate.generater.core.SchemaData;
import org.arch.framework.automate.generater.core.SchemaPattern;
import org.arch.framework.automate.generater.core.SchemaReadable;
import org.arch.framework.automate.generater.core.SchemaType;
import org.arch.framework.automate.generater.core.configuration.XmindConfiguration;
import org.arch.framework.automate.generater.properties.SchemaProperties;
import org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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

    private final ConcurrentHashMap<ReaderConfiguration<XmindConfiguration>, Project> configProjectMap =
                                                                                            new ConcurrentHashMap<>();

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
        XmindUtils.getProject(readerConfiguration, this.configProjectMap).getModules().forEach(module -> {
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
    protected List<? extends SchemaData> readApi(ReaderConfiguration<XmindConfiguration> readerConfiguration) {
        List<MethodSchemaData> methodSchemaDatas = new ArrayList<>();
        XmindUtils.getProject(readerConfiguration, this.configProjectMap).getModules().forEach(module -> {
            module.getApis().forEach(api -> {
                api.getInterfaces().forEach(interfac -> {
                    MethodSchemaData methodSchemaData = new MethodSchemaData();
                    methodSchemaData.setInterfac(interfac);
                    methodSchemaData.setSchemaPattern(SchemaPattern.API);
                    methodSchemaDatas.add(methodSchemaData);
                });
            });
        });
        return methodSchemaDatas;
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
