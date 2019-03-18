package com.tstd2.mybatis.excutor.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ResultSetHandler {

    <E> List<E> handlerResultSet(ResultSet resultSet) throws SQLException;
}
