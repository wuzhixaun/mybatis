import com.wuzx.mapper.IUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author wuzhixuan
 * @Description TODO
 * @createTime 2022-07-28 17:10:00
 */
public class PluginTest {


    SqlSessionFactory sqlSessionFactory;
    @Before
    public void before() throws IOException {
        //加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        //获得sqlSession工厂对象
        sqlSessionFactory= new SqlSessionFactoryBuilder().build(resourceAsStream);

    }


    @Test
    public void test() {

        // 获取sqlsession
        final SqlSession sqlSession = sqlSessionFactory.openSession();

        final IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
        userMapper.getAll();
    }


}
