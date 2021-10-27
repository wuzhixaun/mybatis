package com.lagou.test;

import com.lagou.dao.IUserDao;
import com.lagou.io.Resources;
import com.lagou.pojo.User;
import com.lagou.sqlSession.SqlSession;
import com.lagou.sqlSession.SqlSessionFactory;
import com.lagou.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;

public class IPersistenceTest {


    @Test
    public void test() throws Exception {
        final InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");


        // sqlSessionFactory
        final SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);

        // 拿到session
        final SqlSession sqlSession = sqlSessionFactory.openSession();


        // 操作
        User user = new User();
        user.setId(1);
        user.setUsername("lucy");

        final IUserDao userDao = sqlSession.getMappper(IUserDao.class);
        final User user1 = userDao.findByCondition(user);
        System.out.println(user1);


        // 查询列表
        final List<User> list = userDao.findAll();
        System.out.println(list);


    }
}
