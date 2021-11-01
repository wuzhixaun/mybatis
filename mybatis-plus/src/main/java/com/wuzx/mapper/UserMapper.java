package com.wuzx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuzx.pojo.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    @Select("select * from mp_user")
    public List<User> getAllUser();
}
