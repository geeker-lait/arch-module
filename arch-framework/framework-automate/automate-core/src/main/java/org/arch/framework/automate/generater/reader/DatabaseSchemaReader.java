package org.arch.framework.automate.generater.reader;

import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.from.service.DatabaseService;
import org.arch.framework.automate.generater.core.SchemaMetadata;
import org.arch.framework.automate.generater.core.SchemaReadable;
import org.arch.framework.automate.generater.core.SchemaType;
import org.arch.framework.automate.generater.properties.DatabaseProperties;
import org.arch.framework.automate.generater.properties.SchemaProperties;
import org.arch.framework.automate.generater.properties.TableProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<SchemaMetadata> read(SchemaProperties schemaProperties) {
        return super.read(schemaProperties);
    }

    @Override
    protected List<SchemaMetadata> readMvc(String res, Map<String, String> configuration) {
        List<SchemaMetadata> schemaMetadata = new ArrayList<>();
        DatabaseProperties databaseProperties  = BeanUtil.toBean(configuration,DatabaseProperties.class);
        // 获取数据库的table
        List<TableProperties> tableProperties = databaseService.getDatabaseTablesInfo(databaseProperties, res);
        if (null == tableProperties) {
            tableProperties = new ArrayList<>();
        }
        databaseProperties.setName(res);
        databaseProperties.setTables(tableProperties);
        schemaMetadata.add(databaseProperties);
        return schemaMetadata;
    }

    @Override
    protected List<SchemaMetadata> readApi(String res, Map<String, String> configuration) {
        List<SchemaMetadata> schemaMetadata = new ArrayList<>();

        return schemaMetadata;
    }



    @Override
    public SchemaType getTyp() {
        return SchemaType.DATABASE;
    }



}
