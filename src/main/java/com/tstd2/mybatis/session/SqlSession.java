package com.tstd2.mybatis.session;

import java.util.List;

public interface SqlSession {

    <E> E selectOne(String statement, Object parameter);

    <E> List<E> selectList(String statement, Object parameter);

    <T> T getMapper(Class<T> type);
}
