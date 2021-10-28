import com.wuzx.mapper.IUserMapper;
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

public class CacheTest {




    SqlSessionFactory sqlSessionFactory;
    @Before
    public void before() throws IOException {
        //加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        //获得sqlSession工厂对象
        sqlSessionFactory= new SqlSessionFactoryBuilder().build(resourceAsStream);

    }


    @After
    public void after() {
        System.out.println("关闭资源");

    }


    @Test
    public void firstLevelCache() {

        //获得sqlSession对象(并开启自动提交事务)
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        final IUserMapper mapper = sqlSession.getMapper(IUserMapper.class);


        final User user1 = mapper.findUserById(1);

        // 清空缓存
        sqlSession.clearCache();

        final User user2 = mapper.findUserById(1);
        System.out.println(user2 == user1);
    }


    @Test
    public void secondLevelCache() {
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        SqlSession sqlSession3 = sqlSessionFactory.openSession();

        final IUserMapper mapper1 = sqlSession1.getMapper(IUserMapper.class);
        final IUserMapper mapper2 = sqlSession2.getMapper(IUserMapper.class);
        final IUserMapper mapper3 = sqlSession3.getMapper(IUserMapper.class);


        final User user1 = mapper1.findUserById(1);
        sqlSession1.clearCache();
        final User user2 = mapper2.findUserById(1);
        sqlSession2.clearCache();
        final User user3 = mapper3.findUserById(1);
        sqlSession3.clearCache();


        System.out.println(user1==user2);
    }
}
