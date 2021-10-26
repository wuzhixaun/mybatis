package com.lagou.pojo;

import lombok.Data;
import lombok.ToString;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


/**
 * 配置文件
 */
@Data
@ToString
public class Configuration {

    //数据源
    private DataSource dataSource;

    // map集合，封装了很多mapStatement 对象
    private Map<String, MappedStatement> mappedStatementMap = new HashMap<>();
}
