import com.wuzx.dao.IUserDao;
import com.wuzx.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SqlSessionCRUDTest {


    @Test
    public void find() throws Exception {

        //加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        //获得sqlSession工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //获得sqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //执行sql语句
        List<User> userList = sqlSession.selectList("userMapper.findAll");
        //打印结果
        System.out.println(userList);
        //释放资源
        sqlSession.close();
    }

    @Test
    public void addUser() throws Exception {

        //加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        //获得sqlSession工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //获得sqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();


        User user = new User();
        user.setId(4);
        user.setUsername("张三");
        user.setPassword("123456");
        user.setBirthday("1996-06-06");
        //执行sql语句
        final int insert = sqlSession.insert("userMapper.add", user);
        //打印结果
        System.out.println(insert);

        // 提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
    }


    @Test
    public void updateUser() throws Exception {

        //加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        //获得sqlSession工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //获得sqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(3);
        user.setUsername("shaoxiaozhu");
        user.setPassword("123456");
        user.setBirthday("1996-06-06");
        //执行sql语句
        final int insert = sqlSession.update("userMapper.update", user);
        //打印结果
        System.out.println(insert);

        // 提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
    }

    @Test
    public void deletUser() throws Exception {

        //加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        //获得sqlSession工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //获得sqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();



        //执行sql语句
        final int insert = sqlSession.delete("userMapper.delete", 4);
        //打印结果
        System.out.println(insert);

        // 提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
    }


    @Test
    public void proxy() throws IOException {
        //加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        //获得sqlSession工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //获得sqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取代理对象
        final IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        final List<User> all = userDao.findAll();

        //打印结果
        System.out.println(all);
        //释放资源
        sqlSession.close();
    }



}
