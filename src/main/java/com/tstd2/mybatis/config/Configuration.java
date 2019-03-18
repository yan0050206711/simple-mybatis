package com.tstd2.mybatis.config;

import com.tstd2.mybatis.binding.MapperProxyFaction;
import com.tstd2.mybatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    /**
     * 记录mapper文件的存放位置
     */
    public static final String MAPPER_CONFIG_LOCATION = "config";
    /**
     * 记录数据库连接信息文件存放位置
     */
    public static final String DB_CONFIG_FILE = "db.properties";

    private String dbUrl;

    private String dbUsername;

    private String dbPassword;

    private String dbDriver;

    /**
     * 存放mapper文件解析完以后节点信息
     */
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();

    /**
     * 为mapper接口生成动态代理方法
     */
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return MapperProxyFaction.getMapperProxy(sqlSession, type);
    }

    public static String getMapperConfigLocation() {
        return MAPPER_CONFIG_LOCATION;
    }

    public static String getDbConfigFile() {
        return DB_CONFIG_FILE;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }

    public Map<String, MappedStatement> getMappedStatements() {
        return mappedStatements;
    }

    public MappedStatement getMappedStatement(String statement) {
        return mappedStatements.get(statement);
    }
}
