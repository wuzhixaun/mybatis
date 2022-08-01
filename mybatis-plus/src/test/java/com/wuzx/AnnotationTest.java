package com.wuzx;

import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.wuzx.mapper.UserMapper;
import com.wuzx.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName com.wuzx.AnnotationTest.java
 * @Description 注解测试类
 * @createTime 2021年10月28日 09:56:00
 */
public class AnnotationTest {


    private SqlSession sqlSession;

    @Before
    public void before() throws IOException {
        //加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        //获得sqlSession工厂对象
        SqlSessionFactory sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(resourceAsStream);
        //获得sqlSession对象(并开启自动提交事务)
        sqlSession = sqlSessionFactory.openSession();
    }


    @After
    public void after() {
        System.out.println("关闭资源");
        sqlSession.close();
    }



    @Test
    public void findAll() {
        // 获取代理对象
        final UserMapper userMapper = sqlSession.getMapper(UserMapper.class);


        final List<User> all = userMapper.getAllUser();

        System.out.println(all);
    }


    @Test
    public void findByPlus() {
        // 获取代理对象
        final UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        final List<User> users = userMapper.selectList(null);

        System.out.println(users);

    }





}
