package com.tstd2.mybatis.session;

import com.tstd2.mybatis.config.Configuration;
import com.tstd2.mybatis.config.MappedStatement;
import com.tstd2.mybatis.excutor.Executor;
import com.tstd2.mybatis.excutor.SimpleExecutor;

import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private Executor executor;

    private Configuration conf;

    public DefaultSqlSession(Configuration conf) {
        this.conf = conf;
        this.executor = new SimpleExecutor(conf);
    }

    @Override
    public <E> E selectOne(String statement, Object parameter) {
        List<E> list = this.selectList(statement, parameter);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter) {
        MappedStatement mappedStatement = conf.getMappedStatement(statement);
        try {
            return executor.query(mappedStatement, parameter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return conf.getMapper(type, this);
    }
}
