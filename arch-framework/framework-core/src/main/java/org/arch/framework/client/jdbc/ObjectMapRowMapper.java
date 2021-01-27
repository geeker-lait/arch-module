package org.arch.framework.client.jdbc;

import org.arch.framework.beans.ObjectMap;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * JDBC结果转换类
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
