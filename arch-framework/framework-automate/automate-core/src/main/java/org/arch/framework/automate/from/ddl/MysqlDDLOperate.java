package org.arch.framework.automate.from.ddl;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.arch.framework.automate.api.dto.DefinitionTableDto;
import org.arch.framework.automate.common.utils.JdbcTypeUtils;
import org.arch.framework.automate.from.ddl.sql.MysqlDDLSqlBuilder;
import org.arch.framework.automate.from.utils.DefinitionTableUtil;
import org.arch.framework.automate.generater.properties.ColumnsProperties;
import org.arch.framework.automate.generater.properties.DatabaseProperties;
import org.arch.framework.automate.generater.properties.TableProperties;
import org.arch.framework.beans.exception.BusinessException;
import org.arch.framework.beans.utils.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * jdbc mysql jdbc ddl 操作实现
 * @author junboXiang
 * @version V1.0
 * 2021-02-27
 */
@Slf4j
@Component
public class MysqlDDLOperate extends DDLOperate implements InitializingBean {

    @Override
    protected String getDbUrl(DatabaseProperties properties) {
        return "JDBC:mysql://" + properties.getHost() + ":" + properties.getPort() + "?useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false&serverTimezone=UTC&useSSL=false";
    }

    @Override
    protected String getDriver(DatabaseProperties properties) {
        return "com.mysql.cj.jdbc.Driver";
    }

    @Override
    public int createDatabase(DatabaseProperties properties, String database) {
        Function<Connection, Integer> function = connection -> {
            int existDatabase = existDatabaseOrTable(connection, database, null);
            if (existDatabase > 0) {
                return 1;
            }
            // 库不存在 执行创建库
            try (PreparedStatement statement = connection.prepareStatement(MysqlDDLSqlBuilder.getCreateDatabaseSql(database))) {
                statement.executeUpdate();
            } catch (SQLException sqlException) {
                log.info("createDatabase error DatabaseProperties:{} database:{}", properties, database, sqlException);
                throw new BusinessException("连接操作失败请检查 jdbc 信息是否正确或者账号是否拥有相关权限");
            }
            return 1;
        };
        return execute(properties, function);
    }

    @Override
    public int dropDatabase(DatabaseProperties properties, String database) {
        Function<Connection, Integer> function = connection -> {
            try (PreparedStatement statement = connection.prepareStatement(MysqlDDLSqlBuilder.getDropDatabaseSql(database))) {
                statement.executeUpdate();
            } catch (SQLException sqlException) {
                log.info("dropDatabase error DatabaseProperties:{} database:{}", properties, database, sqlException);
                throw new BusinessException("连接操作失败请检查 jdbc 信息是否正确或者账号是否拥有相关权限");
            }
            return 1;
        };
        return execute(properties, function);
    }

    @Override
    public int dropTable(DatabaseProperties properties, String database, String tableName) {
        Function<Connection, Integer> function = connection -> {
            try (PreparedStatement statement = connection.prepareStatement(MysqlDDLSqlBuilder.getDropTableSql(database, tableName))) {
                statement.executeUpdate();
            } catch (SQLException sqlException) {
                log.info("dropTable error DatabaseProperties:{} database:{}", properties, database, sqlException);
                throw new BusinessException("连接操作失败请检查 jdbc 信息是否正确或者账号是否拥有相关权限");
            }
            return 1;
        };
        return execute(properties, function);
    }

    @Override
    public int createTable(DatabaseProperties properties, DefinitionTableDto record) {
        Function<Connection, Integer> function = connection -> {
            int existDatabase = existDatabaseOrTable(connection, record.getDatabaseName(), record.getTableName());
            if (existDatabase > 0) {
                return 1;
            }
            try (PreparedStatement statement = connection.prepareStatement(MysqlDDLSqlBuilder.getCreateTableSql(record))) {
                statement.executeUpdate();
            } catch (SQLException sqlException) {
                log.info("createTable error DatabaseProperties:{} DefinitionTableDto:{}", properties, record, sqlException);
                throw new BusinessException("连接操作失败请检查 jdbc 信息是否正确或者账号是否拥有相关权限");
            }
            return 1;
        };
        return execute(properties, function);
    }

    @Override
    public List<TableProperties> getDatabaseProperties(DatabaseProperties properties, String database) {
        String databaseNameStr = DefinitionTableUtil.camelToUnderscore(database);
        Function<Connection, List<TableProperties>> function = connection -> {
            // 库不存在
            if (existDatabaseOrTable(connection, databaseNameStr, null) < 0) {
                return null;
            }
            List<TableProperties> tablePropertiesList = new ArrayList<>();
            try (PreparedStatement getTableListStatement = connection.prepareStatement(MysqlDDLSqlBuilder.getDatabaseTablesSql());
                 PreparedStatement getTableDetailStatement = connection.prepareStatement(MysqlDDLSqlBuilder.getTableDetailSql())) {
                getTableListStatement.setString(1, databaseNameStr);
                ResultSet resultSet = getTableListStatement.executeQuery();
                // 1.查询指定库下所有表
                while (resultSet.next()) {
                    TableProperties tableProperties = new TableProperties();
                    String tableName = resultSet.getString("tableName");
                    tableProperties.setName(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, DefinitionTableUtil.lowerUnderscoreToLowerCamel(tableName)));
                    tableProperties.setComment(resultSet.getString("tableComment"));
                    tablePropertiesList.add(tableProperties);
                }
                if (CollectionUtils.isEmpty(tablePropertiesList)) {
                    return null;
                }
                // 2.根据表查询所有字段
                buildColumnsProperties(getTableDetailStatement, tablePropertiesList, databaseNameStr);
            } catch (SQLException sqlException) {
                log.info("getDatabaseProperties error DatabaseProperties:{} database:{}", properties, database, sqlException);
                throw new BusinessException("连接操作失败请检查 jdbc 信息是否正确或者账号是否拥有相关权限");
            }
            return tablePropertiesList;
        };
        return execute(properties, function);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        JDBC_DDL_OPERATE_MAP.put(Constants.DDL_MYSQL, this);
        JDBC_DDL_OPERATE_TYPES.add(Constants.DDL_MYSQL);
    }

    /**
     * 根据表查询所有字段 数据
     * @param getTableDetailStatement
     * @param tablePropertiesList
     * @param databaseNameStr
     * @throws SQLException
     */
    private void buildColumnsProperties(PreparedStatement getTableDetailStatement, List<TableProperties> tablePropertiesList, String databaseNameStr) throws SQLException {
        for (TableProperties tableProperties : tablePropertiesList) {
            getTableDetailStatement.clearParameters();
            getTableDetailStatement.setString(1, DefinitionTableUtil.camelToUnderscore(tableProperties.getName()));
            getTableDetailStatement.setString(2, databaseNameStr);
            ResultSet tableInfoResultSet = getTableDetailStatement.executeQuery();
            List<ColumnsProperties> columnsPropertiesList = Lists.newArrayList();
            while (tableInfoResultSet.next()) {
                String columnName = tableInfoResultSet.getString("columnName");
                String dataType = tableInfoResultSet.getString("dataType").toUpperCase();
                // 字段描述暂时不需要
                String columnType = tableInfoResultSet.getString("columnType");
                String length = columnType.indexOf("(") > 0 ? columnType.substring(columnType.indexOf("(") + 1, columnType.indexOf(")")) : null;
                ColumnsProperties columnsProperties = new ColumnsProperties();
                columnsProperties.setName(DefinitionTableUtil.lowerUnderscoreToLowerCamel(columnName));
                columnsProperties.setTyp(JdbcTypeUtils.getFieldType(dataType).getSimpleName());
                columnsProperties.setLength(length);
                columnsProperties.setComment(tableInfoResultSet.getString("columnComment"));
                columnsPropertiesList.add(columnsProperties);
            }
            tableProperties.setColumns(columnsPropertiesList);
        }
    }

    /**
     * 判断数据库或者表是否存在，如果 只判断数据库是否存在 tableName 传null
     * @param connection
     * @param database
     * @param tableName
     * @return
     */
    private int existDatabaseOrTable(Connection connection, String database, String tableName) {
        int existCount = 0;
        String sql = StringUtils.isBlank(tableName) ? MysqlDDLSqlBuilder.getExistDatabaseSql(database) : MysqlDDLSqlBuilder.getExistTableSql(database, tableName);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                existCount = resultSet.getInt(1);
            }
        } catch (SQLException sqlException) {
            log.info("existDatabaseOrTable error database:{} tableName:{}", database, tableName, sqlException);
            throw new BusinessException("连接操作失败请检查 jdbc 信息是否正确或者账号是否拥有相关权限");
        }
        return existCount;
    }
}
