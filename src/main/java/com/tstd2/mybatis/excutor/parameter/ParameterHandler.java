package com.tstd2.mybatis.excutor.parameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ParameterHandler {
    void setParameters(PreparedStatement prepare) throws SQLException;
}
