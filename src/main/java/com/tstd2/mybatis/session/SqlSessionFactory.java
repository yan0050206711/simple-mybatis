package com.tstd2.mybatis.session;

import com.tstd2.mybatis.config.Configuration;
import com.tstd2.mybatis.config.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;

public class SqlSessionFactory {

    /**
     * 配置对象全局唯一，加载数据库信息和mapper文件信息
     */
    private Configuration conf = new Configuration();

    public SqlSessionFactory() {
        // 加载数据库信息
        loadDbInfo();
        // 加载mapper文件信息
        loadMappersInfo();
    }

    private void loadDbInfo() {
        try {
            Properties prop = new Properties();
            InputStream in = SqlSessionFactory.class.getClassLoader().getResourceAsStream(Configuration.DB_CONFIG_FILE);
            prop.load(in);

            String url = prop.getProperty("url");
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");
            String driver = prop.getProperty("driver");

            conf.setDbUrl(url);
            conf.setDbUsername(username);
            conf.setDbPassword(password);
            conf.setDbDriver(driver);

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void loadMappersInfo() {
        URL resources = SqlSessionFactory.class.getClassLoader().getResource(Configuration.MAPPER_CONFIG_LOCATION);
        File mappers = new File(resources.getFile());
        if (mappers.isDirectory()) {
            File[] listFiles = mappers.listFiles();
            for (File file : listFiles) {
                loadMapperInfo(file);
            }
        }
    }

    private void loadMapperInfo(File file) {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        Element node = document.getRootElement();
        // 获取namespace
        String namespace = node.attribute("namespace").getData().toString();
        // 获取select子节点列表
        List<Element> selects = node.elements("select");
        for (Element element : selects) {
            MappedStatement mappedStatement = new MappedStatement();
            String id = element.attribute("id").getData().toString();
            String resultType = element.attribute("resultType").getData().toString();
            String sql = element.getData().toString();
            String sourceId = namespace + "." + id;

            mappedStatement.setSourceId(sourceId);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSql(sql);
            mappedStatement.setNamespace(namespace);

            // 记录到conf中
            conf.getMappedStatements().put(sourceId, mappedStatement);
        }
    }

    public SqlSession openSession() {
        SqlSession sqlSession = new DefaultSqlSession(conf);
        return sqlSession;
    }
}
