package com.wuzx.pojo;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {

    private int id;
    private String username;
    private String password;
    private String birthday;
}
