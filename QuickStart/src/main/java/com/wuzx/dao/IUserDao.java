package com.wuzx.dao;

import com.wuzx.pojo.User;

import java.util.List;

public interface IUserDao {

    List<User> findAll();
}
