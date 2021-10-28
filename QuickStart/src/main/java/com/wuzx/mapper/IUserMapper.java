package com.wuzx.mapper;

import com.wuzx.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface IUserMapper {

    List<User> findAll();


    List<User> findUserOrder();


    @Insert(" insert into user values(#{id},#{username},#{password},#{birthday}) ")
    public void addUser(User user);

    @Update("update user set username=#{username},password=#{password} where id=#{id}")
    public void updateUser(User user);

    @Select(" select * from user ")
    public List<User> getAll();

    @Delete(" delete from user where id=#{id}")
    public void deleteUser(Integer useId);
}
