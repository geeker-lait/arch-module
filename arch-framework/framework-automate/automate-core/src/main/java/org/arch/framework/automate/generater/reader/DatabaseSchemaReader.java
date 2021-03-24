package org.arch.framework.automate.generater.reader;

import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.from.service.DatabaseService;
import org.arch.framework.automate.generater.core.SchemaMetadata;
import org.arch.framework.automate.generater.core.SchemaPattern;
import org.arch.framework.automate.generater.core.SchemaReadable;
import org.arch.framework.automate.generater.core.SchemaType;
import org.arch.framework.automate.generater.properties.DatabaseProperties;
import org.arch.framework.automate.generater.properties.MethodProperties;
import org.arch.framework.automate.generater.properties.SchemaProperties;
import org.arch.framework.automate.generater.properties.TableProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/26/2021 11:05 AM
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseSchemaReader extends AbstractSchemaReader implements SchemaReadable {

    private final DatabaseService databaseService;

    @Override
    public SchemaType getTyp() {
        return SchemaType.DATABASE;
    }

    @Override
    public List<SchemaMetadata> read(SchemaProperties schemaProperties) {
        return super.doRead(schemaProperties);
    }

    @Override
    protected List<SchemaMetadata> readMvc(String res, Map<String, String> configuration) {
        DatabaseProperties databaseProperties  = BeanUtil.toBean(configuration,DatabaseProperties.class);
        // 获取数据库的table
        List<TableProperties> tableProperties = databaseService.getDatabaseTablesInfo(databaseProperties, res);
        if (null == tableProperties) {
            tableProperties = new ArrayList<>();
        }
        databaseProperties.setName(res);
        databaseProperties.setTables(tableProperties);
        databaseProperties.setPattern(SchemaPattern.MVC.getPattern());
        return Arrays.asList(databaseProperties);
    }

    @Override
    protected List<SchemaMetadata> readApi(String res, Map<String, String> configuration) {
        DatabaseProperties databaseProperties  = BeanUtil.toBean(configuration,DatabaseProperties.class);
        // 获取数据库的table
        List<MethodProperties> tableProperties = databaseService.getDatabaseApisInfo(databaseProperties, res);
        databaseProperties.setName(res);
        databaseProperties.setApis(tableProperties);
        databaseProperties.setPattern(SchemaPattern.API.getPattern());

        return Arrays.asList(databaseProperties);
    }





}
