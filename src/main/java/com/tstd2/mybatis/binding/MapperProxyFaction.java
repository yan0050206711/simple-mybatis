package com.tstd2.mybatis.binding;

import com.tstd2.mybatis.session.SqlSession;

import java.lang.reflect.Proxy;

public class MapperProxyFaction {

    public static <T> T getMapperProxy(SqlSession sqlSession, Class<T> mapperInterface) {
        MapperProxy<T> mapperProxy = new MapperProxy(sqlSession, mapperInterface);
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, mapperProxy);
    }

}
