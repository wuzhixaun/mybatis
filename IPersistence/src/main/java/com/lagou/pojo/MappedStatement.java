package com.lagou.pojo;


import lombok.Data;
import lombok.ToString;

/**
 * sql语句、statement类型、输入参数java类型、输出参数java类型
 */
@Data
@ToString
public class MappedStatement {

    // id
    private String id; //sql语句
    private String sql; //输入参数
    private Class<?> paramterType; //输出参数
    private Class<?> resultType;

}
