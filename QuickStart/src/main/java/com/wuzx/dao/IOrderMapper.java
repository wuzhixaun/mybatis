package com.wuzx.dao;

import com.wuzx.pojo.Order;

import java.util.List;

public interface IOrderMapper {

    public List<Order> findOrderAndUser();
}
