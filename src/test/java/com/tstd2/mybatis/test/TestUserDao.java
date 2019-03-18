package com.tstd2.mybatis.test;

import com.tstd2.mybatis.session.SqlSession;
import com.tstd2.mybatis.session.SqlSessionFactory;
import org.junit.Test;

public class TestUserDao {

    @Test
    public void testQuery() {
        SqlSessionFactory factory = new SqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);

        User user = userDao.getUserById(1);
        System.out.println(user);
    }

}
