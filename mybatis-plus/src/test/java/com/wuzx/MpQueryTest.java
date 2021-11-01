package com.wuzx;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuzx.mapper.UserMapper;
import com.wuzx.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MpQueryTest {

    @Autowired
    private UserMapper userMapper;


    @Test
    public void queryById() {

        final User user = userMapper.selectById(1L);

        System.out.println(user);
    }

    @Test
    public void selectbathByid() {

        final List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        System.out.println(users);
    }

    @Test
    public void selectOne() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "wuzhixuan");
        final User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    @Test
    public void selectList() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "wuzhixuan");
        final List<User> users = userMapper.selectList(queryWrapper);
        System.out.println(users);
    }

    @Test
    public void selectCount() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("age", 18);
        final Long count = userMapper.selectCount(queryWrapper);
        System.out.println(count);
    }


    /**
     * 分页查询
     */

    @Test
    public void selectPage() {

        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.gt("age", 20); //年龄大于20岁

        Page<User> page = new Page<>(1,1);

        IPage<User> iPage = this.userMapper.selectPage(page, wrapper);
        System.out.println("数据总条数:" + iPage.getTotal());
        System.out.println("总⻚数:" + iPage.getPages());

        List<User> users = iPage.getRecords();
        for (User user : users) {
            System.out.println("user = " + user);
        }
    }
}
