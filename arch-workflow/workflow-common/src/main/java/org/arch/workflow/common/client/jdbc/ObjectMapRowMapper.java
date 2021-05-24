package org.arch.workflow.common.client.jdbc;

import org.arch.workflow.common.model.ObjectMap;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * JDBC结果转换类
 *
 * @author lait.zhang@gmail.com
 * @date 2018/12/6
 */
public class ObjectMapRowMapper implements RowMapper<ObjectMap> {

    @Override
    public ObjectMap mapRow(ResultSet rs, int rowNum) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        ObjectMap mapOfColValues = new ObjectMap();
        for (int i = 1; i <= columnCount; i++) {
            String key = JdbcUtils.lookupColumnName(rsmd, i);
            Object obj = JdbcUtils.getResultSetValue(rs, i, String.class);
            mapOfColValues.put(key, obj);
        }
        return mapOfColValues;
    }

}