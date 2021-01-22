package com.unichain.pay.channel.mfe88.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IResultSetCall<T> {

    T invoke(ResultSet rs) throws SQLException;

}
