package com.lagou.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class User {

    private Integer id;

    private String username;


    private String password;

    private String birthday;
}
