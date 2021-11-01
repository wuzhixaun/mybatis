package com.wuzx;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wuzx.mapper.UserMapper;
import com.wuzx.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        List<User> userList = userMapper.selectList(null);
        for (User user : userList) {
            System.out.println(user);
        }
    }


    @Test
    public void insert() {
        User user = new User();

        user.setAge(25);
        user.setName("zhangsan3");
        user.setEmail("zhangsan@qq.com");
        // 这个返回值就是影响行数
        final int insert = userMapper.insert(user);

        System.out.println(user.getId());
    }


    @Test
    public void selectById() {

        final User user = userMapper.selectById(2);
        System.out.println(user);
    }


    @Test
    public void update2() {

        // 使用QueryWrapper
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", 7).set("email","heheheh");


        final int update = userMapper.update(null,updateWrapper);
        System.out.println(update);
    }

    @Test
    public void deleteById() {
        userMapper.deleteById(7);
    }


    @Test
    public void deleteByMap() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("name", "zhangsan1");

        userMapper.deleteByMap(map);

    }

    @Test
    public void deleteByWrapper() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "zhangsan2");
        userMapper.delete(queryWrapper);

    }


    @Test
    public void delete() {
        userMapper.deleteBatchIds(Arrays.asList(8, 11));
    }




}
