package com.tstd2.mybatis.excutor.statement;

import com.tstd2.mybatis.config.MappedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatementHandler {

    private MappedStatement mappedStatement;

    public StatementHandler(MappedStatement mappedStatement) {
        this.mappedStatement = mappedStatement;
    }

    public PreparedStatement prepare(Connection conn) throws SQLException {
        PreparedStatement pst = conn.prepareStatement(mappedStatement.getSql());
        return pst;
    }

    public ResultSet query(PreparedStatement prepare) throws SQLException {
        ResultSet rs = prepare.executeQuery();
        return rs;
    }
}
