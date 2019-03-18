package com.tstd2.mybatis.excutor;

import com.tstd2.mybatis.config.MappedStatement;

import java.sql.SQLException;
import java.util.List;

public interface Executor {

    <E> List<E> query(MappedStatement ms, Object parameter) throws SQLException;

}
