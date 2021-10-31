package com.wuzx.mapper;

import com.wuzx.pojo.User;
import org.apache.ibatis.annotations.*;
import org.mybatis.caches.redis.RedisCache;

import java.util.List;
//@CacheNamespace(implementation = RedisCache.class) // 开启二级缓存
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

    @Select("select * from user where id =#{id}")
    public User findUserById(Integer userId);


    @Select(" select  * from user where id =#{adc}")
    User findById(Integer id);


    /**
     *     private String username;
     *     private String password;
     *     private String birthday;
     * @return
     */
    @Select("select * from user")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "password",column = "password"),
            @Result(property = "birthday",column = "birthday"),
            @Result(property = "orderList",javaType = List.class,column = "id",many = @Many(select = "com.wuzx.mapper.IOrderMapper.findByUserId"))
    })
    List<User> findAllByAn();
}
