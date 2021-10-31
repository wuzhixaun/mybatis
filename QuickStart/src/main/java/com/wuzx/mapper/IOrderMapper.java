package com.wuzx.mapper;

import com.wuzx.pojo.Order;
import com.wuzx.pojo.User;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IOrderMapper {

    public List<Order> findOrderAndUser();



    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "ordertime", column = "ordertime"),
            @Result(property = "total", column = "total"),
            @Result(property = "user", column = "uid", javaType = User.class, one = @One(select = "com.wuzx.mapper.IUserMapper.findById"))
    })
    @Select("select * from orders")
    public List<Order> findAll();


    @Select("select * from orders where uid = #{userId}")
    List<Order> findByUserId(Integer userId);

}
