package com.lagou.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {

    //主键标识
    private Integer id;
    //用户名
    private String username;
}
