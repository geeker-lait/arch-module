package org.arch.framework.automate.generater.reader;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.core.SchemaData;
import org.arch.framework.automate.generater.core.SchemaReadable;
import org.arch.framework.automate.generater.core.SchemaType;
import org.arch.framework.automate.generater.properties.DatabaseProperties;
import org.arch.framework.automate.generater.properties.SchemaProperties;
import org.arch.framework.automate.generater.properties.TableProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/26/2021 11:05 AM
 */
@Slf4j
@Service
public class DatabaseSchemaReader extends AbstractSchemaReader implements SchemaReadable<DatabaseProperties> {

    public List<DatabaseProperties> read1(DatabaseProperties source) {
        List<DatabaseProperties> databasePropertiesList = new ArrayList<>();
        Arrays.asList(source.getName().split(",")).forEach(dbname -> {
            // 获取数据库的table
            List<TableProperties> tableProperties = databaseService.getDatabaseTablesInfo(source, dbname);
            if (null == tableProperties) {
                tableProperties = new ArrayList<>();
            }
            if (null == source.getTables()) {
                source.setTables(tableProperties);
            }
            // 追加自定义的table
            tableProperties.addAll(source.getTables());
            DatabaseProperties databaseProperties = new DatabaseProperties();
            databaseProperties.setTables(tableProperties);
            databasePropertiesList.add(databaseProperties);
        });
        return databasePropertiesList;
    }


    @Override
    public SchemaType getTyp() {
        return SchemaType.DATABASE;
    }

    @Override
    public <T extends SchemaData> List<T> read(SchemaProperties schemaProperties) {
        return null;
    }


}
