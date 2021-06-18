package org.arch.framework.encrypt.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ff3 type handler
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.20 20:50
 */
public class Ff3TypeHandler extends BaseTypeHandler<Object> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        // TODO

    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        // TODO
        return null;
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        // TODO
        return null;
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        // TODO
        return null;
    }
}
