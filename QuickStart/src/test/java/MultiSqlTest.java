import com.wuzx.dao.IOrderMapper;
import com.wuzx.dao.IUserDao;
import com.wuzx.pojo.Order;
import com.wuzx.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MultiSqlTest {


    @Test
    public void getOrder() throws IOException {
        //加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        //获得sqlSession工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //获得sqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取代理对象
        final IOrderMapper orderMapper = sqlSession.getMapper(IOrderMapper.class);
        final List<Order> all = orderMapper.findOrderAndUser();

        //打印结果
        System.out.println(all);
        //释放资源
        sqlSession.close();
    }
}
