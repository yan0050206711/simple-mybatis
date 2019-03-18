package com.tstd2.mybatis.excutor.parameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DefaultParameterHandler implements ParameterHandler {

    private Object parameter;

    public DefaultParameterHandler(Object parameter) {
        this.parameter = parameter;
    }

    public Object getParameter() {
        return parameter;
    }

    public void setParameter(Object parameter) {
        this.parameter = parameter;
    }

    @Override
    public void setParameters(PreparedStatement prepare) throws SQLException {
        if (parameter == null) {
            return;
        }

        if (parameter.getClass().isArray()) {
            Object[] paramArray = (Object[]) parameter;
            for (int i = 0; i < paramArray.length; i++) {
                if (paramArray[i] instanceof Integer) {
                    prepare.setInt(i + 1, (int) paramArray[i]);
                }
                if (paramArray[i] instanceof String) {
                    prepare.setString(i + 1, (String) paramArray[i]);
                }
                if (paramArray[i] instanceof Long) {
                    prepare.setLong(i + 1, (Long) paramArray[i]);
                }
            }
        }
    }
}
