package com.lagou.dao;

import com.lagou.pojo.User;

import java.util.List;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName IUserDao.java
 * @Description TODO
 * @createTime 2021年10月27日 10:44:00
 */
public interface IUserDao {

    /**
     * 查询所有用户
     * @return
     * @throws Exception
     */
    public List<User> findAll() throws Exception;


    /**
     * 根据条件进行用户查询
     * @param user
     * @return
     * @throws Exception
     */
    public User findByCondition(User user) throws Exception;
}
