package com.wuzx.pojo;


import lombok.Data;
import lombok.ToString;

/**
 * 订单表
 */
@Data
@ToString
public class Order {
    private Integer id;
    private Integer uid;
    private String ordertime;
    private Double total;

    // 该订单属于某个用户
    private User user;
}
