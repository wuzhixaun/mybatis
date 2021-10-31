import com.wuzx.mapper.IOrderMapper;
import com.wuzx.mapper.IUserMapper;
import com.wuzx.pojo.Order;
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
 * @ClassName AnnotationTest.java
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
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //获得sqlSession对象(并开启自动提交事务)
        sqlSession = sqlSessionFactory.openSession(true);
    }


    @After
    public void after() {
        System.out.println("关闭资源");
        sqlSession.close();
    }


    @Test
    public void addUser() {
        // 获取代理对象
        final IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);

        User user = new User();
        user.setId(4);
        user.setUsername("zhangsan");
        user.setBirthday("11111");
        userMapper.addUser(user);
    }


    @Test
    public void updateUser() {
        // 获取代理对象
        final IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);

        User user = new User();
        user.setId(4);
        user.setUsername("wangwu");
        user.setBirthday("2222");
        userMapper.updateUser(user);
    }


    @Test
    public void deleteUser() {
        // 获取代理对象
        final IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);


        userMapper.deleteUser(4);
    }


    @Test
    public void findAll() {
        // 获取代理对象
        final IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);


        final List<User> all = userMapper.getAll();

        System.out.println(all);
    }


    @Test
    public void findAllOrder() {
        // 获取代理对象
        final IOrderMapper orderMapper = sqlSession.getMapper(IOrderMapper.class);


        final List<Order> all = orderMapper.findAll();

        for (Order order : all) {
            System.out.println(order);
        }
    }


    @Test
    public void findAllUser() {
        // 获取代理对象
        final IUserMapper mapper = sqlSession.getMapper(IUserMapper.class);


        final List<User> all = mapper.findAllByAn();

        for (User user : all) {
            System.out.println(user);
        }
    }


}
