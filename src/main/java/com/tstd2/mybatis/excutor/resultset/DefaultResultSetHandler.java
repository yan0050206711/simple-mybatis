package com.tstd2.mybatis.excutor.resultset;

import com.tstd2.mybatis.config.MappedStatement;
import com.tstd2.mybatis.reflection.ReflectionUtil;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefaultResultSetHandler implements ResultSetHandler {

    private MappedStatement mappedStatement;

    public DefaultResultSetHandler(MappedStatement mappedStatement) {
        this.mappedStatement = mappedStatement;
    }

    @Override
    public <E> List<E> handlerResultSet(ResultSet resultSet) throws SQLException {

        List<E> ret = new ArrayList<>();
        while (resultSet.next()) {
            try {
                Class<?> entityClass = Class.forName(mappedStatement.getResultType());
                E entity = (E) entityClass.newInstance();
                Field[] declaredFields = entityClass.getDeclaredFields();
                for (int i = 0; i < declaredFields.length; i++) {
                    Class<?> fieldType = declaredFields[i].getType();
                    Object fieldValue = null;
                    if (fieldType == String.class) {
                        fieldValue = resultSet.getString(i + 1);
                    } else if (fieldType == Integer.class || fieldType == int.class) {
                        fieldValue = resultSet.getInt(i + 1);
                    } else if (fieldType == Long.class || fieldType == long.class) {
                        fieldValue = resultSet.getLong(i + 1);
                    } else if (fieldType == Double.class || fieldType == double.class) {
                        fieldValue = resultSet.getDouble(i + 1);
                    } else if (fieldType == Float.class || fieldType == float.class) {
                        fieldValue = resultSet.getFloat(i + 1);
                    } else if (fieldType == Byte.class || fieldType == byte.class) {
                        fieldValue = resultSet.getByte(i + 1);
                    } else if (fieldType == Boolean.class || fieldType == boolean.class) {
                        fieldValue = resultSet.getBoolean(i + 1);
                    } else if (fieldType == Date.class) {
                        fieldValue = resultSet.getDate(i + 1);
                    }

                    ReflectionUtil.setPropToBean(entity, declaredFields[i].getName(), fieldValue);
                }
                ret.add(entity);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        return ret;
    }
}
