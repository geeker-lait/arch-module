package org.arch.framework.automate.from.service;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.arch.framework.automate.from.mapper.DDLMapper;
import org.arch.framework.automate.from.utils.DefinitionTableUtil;
import org.arch.framework.automate.generater.properties.ColumnsProperties;
import org.arch.framework.automate.generater.properties.TableProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author junboXiang
 * @version V1.0
 * 2021-02-22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseService {
    private final DDLMapper ddlMapper;

    /**
     * 根据数据库名称获取数 及 表相关属性数据
     * @param database
     * @return
     */
    public List<TableProperties> getDatabaseTablesInfo(String database) {
        String databaseName = DefinitionTableUtil.camelToUnderscore(database);
        List<Map<String, String>> databaseTables = ddlMapper.getDatabaseTables(databaseName);
        if (CollectionUtils.isEmpty(databaseTables)) {
            return null;
        }

        List<TableProperties> tables = Lists.newArrayList();
        databaseTables.forEach(tableMap -> {
            String tableName = tableMap.get("tableName");
            TableProperties tableProperties = new TableProperties();

            tableProperties.setName(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, DefinitionTableUtil.lowerUnderscoreToLowerCamel(tableName)));
            tableProperties.setComment(tableMap.get("tableComment"));
            tableProperties.setColumns(buildMysqlTableColumns(databaseName, tableName));
            tables.add(tableProperties);
        });

        return tables;
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
            String columnName = detail.get("columnName");
            String dataType = detail.get("dataType").toUpperCase();
            // 字段描述暂时不需要
//            String columnComment = detail.get("columnComment");
            String columnType = detail.get("columnType");
            String length = columnType.indexOf("(") > 0 ? columnType.substring(columnType.indexOf("(") + 1, columnType.indexOf(")")) : null;
            ColumnsProperties columnsProperties = new ColumnsProperties();
            columnsProperties.setName(DefinitionTableUtil.lowerUnderscoreToLowerCamel(columnName));
            columnsProperties.setTyp(DefinitionTableUtil.getFieldType(dataType).getSimpleName());
            columnsProperties.setLength(length);
            columnsPropertiesList.add(columnsProperties);
        });
        return columnsPropertiesList;
    }

}
