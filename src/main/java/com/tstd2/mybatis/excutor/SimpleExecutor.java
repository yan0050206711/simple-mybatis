package com.tstd2.mybatis.excutor;

import com.tstd2.mybatis.config.Configuration;
import com.tstd2.mybatis.config.MappedStatement;
import com.tstd2.mybatis.excutor.parameter.DefaultParameterHandler;
import com.tstd2.mybatis.excutor.parameter.ParameterHandler;
import com.tstd2.mybatis.excutor.statement.StatementHandler;
import com.tstd2.mybatis.excutor.resultset.DefaultResultSetHandler;
import com.tstd2.mybatis.excutor.resultset.ResultSetHandler;

import java.sql.*;
import java.util.List;

public class SimpleExecutor implements Executor {

    private Configuration conf;

    public SimpleExecutor(Configuration conf) {
        this.conf = conf;
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter) throws SQLException {
        MappedStatement mappedStatement = conf.getMappedStatement(ms.getSourceId());
        // 1.获取connection对象
        Connection conn = this.getConnection();
        // 2.实例化StatementHandler对象，准备实例化Statement
        StatementHandler statementHandler = new StatementHandler(mappedStatement);
        // 3.通过statementHandler和connection获取PreparedStatement
        PreparedStatement prepare = statementHandler.prepare(conn);
        // 4.实例化ParameterHandler对象，对Statement中sql语句进行占位符处理
        ParameterHandler parameterHandler = new DefaultParameterHandler(parameter);
        parameterHandler.setParameters(prepare);
        // 5.执行sql语句，获取执行结果集resultSet
        ResultSet resultSet = statementHandler.query(prepare);
        // 6.实例化ResultSetHandler对象，对resultSet中结果进行处理，转换成对象
        ResultSetHandler resultSetHandler = new DefaultResultSetHandler(mappedStatement);
        return resultSetHandler.handlerResultSet(resultSet);
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(conf.getDbDriver());
            conn = DriverManager.getConnection(conf.getDbUrl(), conf.getDbUsername(), conf.getDbPassword());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
