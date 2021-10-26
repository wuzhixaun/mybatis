package com.lagou.test;

import com.lagou.io.Resources;
import com.lagou.pojo.User;
import com.lagou.sqlSession.SqlSession;
import com.lagou.sqlSession.SqlSessionFactory;
import com.lagou.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class IPersistenceTest {


    public void test() throws PropertyVetoException, DocumentException, ClassNotFoundException {
        final InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");


        // sqlSessionFactory
        final SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);

        // 拿到session
        final SqlSession sqlSession = sqlSessionFactory.openSession();


        // 操作
        User user = new User();
        user.setId(1);
        user.setUsername("zhangsan");
        final Object o = sqlSession.selectOne("user.selectOne", user);
    }
}
