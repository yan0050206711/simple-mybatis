package com.tstd2.mybatis.binding;

import com.tstd2.mybatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;

public class MapperProxy<T> implements InvocationHandler {

    private SqlSession sqlSession;

    private Class<T> mapperInterface;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    private <T> boolean isCollection(Class<T> type) {
        return Collection.class.isAssignableFrom(type);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 如果是Object本身的方法则不进行代理
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }

        Class<?> returnType = method.getReturnType();
        Object ret = null;
        String statement = mapperInterface.getName() + "." + method.getName();
        if (isCollection(returnType)) {
            ret = sqlSession.selectList(statement, args);
        } else {
            ret = sqlSession.selectOne(statement, args);
        }

        return ret;
    }
}
