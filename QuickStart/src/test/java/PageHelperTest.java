import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wuzx.mapper.IUserMapper;
import com.wuzx.mapper.UserMapper;
import com.wuzx.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PageHelperTest {

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

    @Test
    public void testPageHelper() {
        PageHelper.startPage(1, 2);

        final IUserMapper mapper = sqlSession.getMapper(IUserMapper.class);

        final List<User> all = mapper.findAll();

        final PageInfo<User> userPageInfo = new PageInfo<>(all);

        System.out.println(userPageInfo);
    }

    @Test
    public void test() {
        final UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User user = new User();
        user.setId(1);

        final User user1 = mapper.selectOne(user);
        System.out.println(user1);
    }

    @Test
    public void example() {
        final UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        final Example example = new Example(User.class);

        example.createCriteria().andEqualTo("id", 3);

        final List<User> users = mapper.selectByExample(example);

        System.out.println(users);
    }
}
