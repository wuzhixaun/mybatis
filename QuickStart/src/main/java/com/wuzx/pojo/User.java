package com.wuzx.pojo;


import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class User {

    private int id;
    private String username;
    private String password;
    private String birthday;

    private List<Order> orderList;
}
