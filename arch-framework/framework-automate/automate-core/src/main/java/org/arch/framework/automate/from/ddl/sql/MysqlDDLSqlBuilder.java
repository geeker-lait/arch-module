package org.arch.framework.automate.from.ddl.sql;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.arch.framework.automate.api.dto.DefinitionTableDto;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author junboXiang
 * @version V1.0
 * 2021-02-27
 */
@Slf4j
@Component
public class MysqlDDLSqlBuilder {

    /**
     * 获取 判断数据库是否存在 sql
     *
     * @param database
     * @return
     */
    public static String getExistDatabaseSql(String database) {
        String sql = "SELECT count(*)\n" +
                "        FROM information_schema.SCHEMATA\n" +
                "        WHERE LCASE(SCHEMA_NAME) = ''{0}'';";
        return MessageFormat.format(sql, database);
    }

    /**
     * 获取 创建数据库 sql
     *
     * @param database
     * @return
     */
    public static String getCreateDatabaseSql(String database) {
        String sql = "CREATE DATABASE IF NOT EXISTS `{0}` Character Set utf8mb4 collate utf8mb4_general_ci;";
        return MessageFormat.format(sql, database);
    }

    /**
     * 获取 删除指定数据库 sql
     *
     * @param database
     * @return
     */
    public static String getDropDatabaseSql(String database) {
        String sql = "DROP DATABASE IF EXISTS `{0}`;";
        return MessageFormat.format(sql, database);
    }

    /**
     * 获取 判断指定库下表是否存在 sql
     *
     * @param database
     * @param tableName
     * @return
     */
    public static String getExistTableSql(String database, String tableName) {
        String sql = "SELECT count(*)\n" +
                "        FROM information_schema.TABLES\n" +
                "        WHERE LCASE(TABLE_NAME) = ''{0}''\n" +
                "        AND LCASE(TABLE_SCHEMA) = ''{1}'';";
        return MessageFormat.format(sql, database, tableName);
    }

    /**
     * 获取 删除表 sql
     *
     * @param database
     * @param tableName
     * @return
     */
    public static String getDropTableSql(String database, String tableName) {
        String sql = "DROP TABLE IF EXISTS `{0}`.`{1}`";
        return MessageFormat.format(sql, database, tableName);
    }


    /**
     * 获取 创建 table sql
     *
     * @param record
     * @return
     */
    public static String getCreateTableSql(DefinitionTableDto record) {
        StringBuilder stringBuilder = new StringBuilder();
        String begin = MessageFormat.format("CREATE TABLE IF NOT EXISTS `{0}`.`{1}` (\n", record.getDatabaseName(), record.getTableName());
        stringBuilder.append(begin);
        List<String> fieldAndIndexList = new ArrayList<>();
        // 判断是否有主键, 没有默认使用 bigint(19)作为主键
        boolean pkIsBlank = StringUtils.isBlank(record.getPk());
        if (pkIsBlank) {
            fieldAndIndexList.add("\tid bigint(19) NOT NULL AUTO_INCREMENT COMMENT '自增主键'");
        }
        // 循环处理 字段(字段集合不用判断空,没有字段 就是json不合法)
        for (DefinitionTableDto.Field field : record.getFieldList()) {
            String fieldStr = MessageFormat.format("\t`{0}` {1}({2}) ", field.getName(), field.getJdbcType(), field.getLength()) +
                    (field.getNotNull() ? "  NOT NULL " : "");
            String fieldComment = StringUtils.isNotBlank(field.getComment()) ? MessageFormat.format(" COMMENT ''{0}'' ", field.getComment()) : "";
            fieldAndIndexList.add(fieldStr + fieldComment);
        }
        // 处理 外键 (外键对其他表有依赖,创建表的时候暂不处理)
        // 处理 唯一索引
        if (CollectionUtils.isNotEmpty(record.getUniqueList())) {
            for (DefinitionTableDto.Unique unique : record.getUniqueList()) {
                String format = MessageFormat.format("\tUNIQUE `{0}` ( ", unique.getName());
                String join = Joiner.on(",").join(unique.getFieldList().stream().map(str -> "`" + str + "`").collect(Collectors.toList())) + " ) ";
                String uniqueComment = StringUtils.isNotBlank(unique.getComment()) ? MessageFormat.format(" COMMENT ''{0}'' ", unique.getComment()) : "";
                fieldAndIndexList.add(format + join + uniqueComment);
            }
        }
        // 处理 普通索引
        if (CollectionUtils.isNotEmpty(record.getIndexList())) {
            for (DefinitionTableDto.Index index : record.getIndexList()) {
                String format = MessageFormat.format("\tINDEX `{0}` ( ", index.getName());
                String join = Joiner.on(",").join(index.getFieldList().stream().map(str -> "`" + str + "`").collect(Collectors.toList())) + ") ";
                String indexComment = StringUtils.isNotBlank(index.getComment()) ? MessageFormat.format(" COMMENT ''{0}'' ", index.getComment()) : "";
                fieldAndIndexList.add(format + join + indexComment);
            }
        }
        // 处理 主键 设置主键
        String pkStr = "\tPRIMARY KEY (`{0}`) ";
        String formatValue = pkIsBlank ? "id" : record.getPk();
        fieldAndIndexList.add(MessageFormat.format(pkStr, formatValue));
        stringBuilder.append(Joiner.on(",\n").skipNulls().join(fieldAndIndexList));
        stringBuilder.append("\t )\n");
        if (StringUtils.isNotBlank(record.getComment())) {
            String tableComment = MessageFormat.format(" COMMENT = ''{0}'';", record.getComment());
            stringBuilder.append(tableComment);
        } else {
            stringBuilder.append(";");
        }
        return stringBuilder.toString();
    }

    /**
     * 获取 指定库下所有的表 sql
     * 只返回 完整的sql, 不做任何替换，jdbc执行的时候通过PreparedStatement设置对应的值
     *
     * @return
     */
    public static String getDatabaseTablesSql() {
        String sql = "SELECT TABLE_NAME tableName, TABLE_COMMENT tableComment\n" +
                "        FROM information_schema.TABLES\n" +
                "        WHERE LCASE(table_schema) = ?  ORDER BY table_name";
        return sql;
    }

    /**
     * 获取 指定库下的指定表的所有字段信息 sql
     * 只返回 完整的sql, 不做任何替换，jdbc执行的时候通过PreparedStatement设置对应的值
     *
     * @return
     */
    public static String getTableDetailSql() {
        String sql = "SELECT COLUMN_NAME columnName, DATA_TYPE dataType,COLUMN_COMMENT columnComment, COLUMN_TYPE columnType\n" +
                "        FROM information_schema.columns\n" +
                "        WHERE LCASE(TABLE_NAME) = ? and LCASE(TABLE_SCHEMA) = ? ORDER BY ordinal_position";
        return sql;
    }

}
