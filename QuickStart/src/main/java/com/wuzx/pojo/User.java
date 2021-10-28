package com.wuzx.pojo;


import lombok.Data;
import lombok.Generated;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Data
@ToString
@Table(name = "user")
public class User  implements Serializable {

    @Id //逐渐id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //设置主键生成策略
    private int id;
    private String username;
    private String password;
    private String birthday;
//
//    private List<Order> orderList;
}
