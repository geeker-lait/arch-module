package org.arch.framework.automate.from.ddl;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.arch.framework.automate.api.dto.DefinitionTableDto;
import org.arch.framework.automate.common.utils.JdbcTypeUtils;
import org.arch.framework.automate.from.mapper.DDLMapper;
import org.arch.framework.automate.from.utils.DefinitionTableUtil;
import org.arch.framework.automate.generater.properties.ColumnsProperties;
import org.arch.framework.automate.generater.properties.DatabaseProperties;
import org.arch.framework.automate.generater.properties.TableProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author junboXiang
 * @version V1.0
 * 2021-02-27
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultDDLOperate extends DDLOperate {

    private final DDLMapper ddlMapper;


    @Override
    protected String getDbUrl(DatabaseProperties properties) {
        return null;
    }

    @Override
    protected String getDriver(DatabaseProperties properties) {
        return null;
    }

    @Override
    public int createDatabase(DatabaseProperties properties, String database) {
        if (ddlMapper.existDatabase(database) > 0) {
            log.info("database exist databaseProperties:{} dbName:{}", properties, database);
            return 1;
        }
        return ddlMapper.createDatabase(database);
    }

    @Override
    public int dropDatabase(DatabaseProperties properties, String database) {
        return ddlMapper.dropDatabase(database);
    }

    @Override
    public int dropTable(DatabaseProperties properties, String database, String tableName) {
        return ddlMapper.dropTable(database, tableName);
    }

    @Override
    public int createTable(DatabaseProperties properties, DefinitionTableDto record) {
        if (ddlMapper.existTable(record.getDatabaseName(), record.getTableName()) > 0) {
            log.info("table exist databaseProperties:{} definitionTableDto:{}", properties, record);
            return 1;
        }
        return ddlMapper.createTable(record);
    }

    @Override
    public List<TableProperties> getDatabaseProperties(DatabaseProperties properties, String database) {
        String databaseNameStr = DefinitionTableUtil.camelToUnderscore(database);
        // 查询当前库 下边有多少表
        List<Map<String, String>> databaseTables = ddlMapper.getDatabaseTables(databaseNameStr);
        if (CollectionUtils.isEmpty(databaseTables)) {
            return null;
        }
        // 遍历每个表的字段数据
        return databaseTables.stream().map(tableMap -> {
            TableProperties tableProperties = new TableProperties();
            String tableName = tableMap.get("tableName");
            tableProperties.setName(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, DefinitionTableUtil.lowerUnderscoreToLowerCamel(tableName)));
            tableProperties.setComment(tableMap.get("tableComment"));
            tableProperties.setColumns(buildMysqlTableColumns(databaseNameStr, tableName));
            return tableProperties;
        }).collect(Collectors.toList());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        JDBC_DDL_OPERATE_MAP.put(Constants.DDL_DEFAULT, this);
    }

    /**
     * 获取列详情
     * @param databaseName
     * @param tableName
     * @return
     */
    private List<ColumnsProperties> buildMysqlTableColumns(String databaseName, String tableName) {
        List<Map<String, String>> tableDetail = ddlMapper.getTableDetail(databaseName, tableName);
        List<ColumnsProperties> columnsPropertiesList = Lists.newArrayList();
        tableDetail.forEach(detail -> {
            ColumnsProperties columnsProperties = new ColumnsProperties();
            String columnName = detail.get("columnName");
            String dataType = detail.get("dataType").toUpperCase();
            // 字段描述暂时不需要
            String columnType = detail.get("columnType");
            String length = columnType.indexOf("(") > 0 ? columnType.substring(columnType.indexOf("(") + 1, columnType.indexOf(")")) : null;
            columnsProperties.setName(DefinitionTableUtil.lowerUnderscoreToLowerCamel(columnName));
            columnsProperties.setTyp(JdbcTypeUtils.getFieldType(dataType).getSimpleName());
            columnsProperties.setLength(length);
            columnsProperties.setComment(detail.get("columnComment"));
            columnsPropertiesList.add(columnsProperties);
        });
        return columnsPropertiesList;
    }
}
