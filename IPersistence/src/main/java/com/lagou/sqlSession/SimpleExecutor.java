package com.lagou.sqlSession;

import com.lagou.config.BoundSql;
import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import com.lagou.utils.GenericTokenParser;
import com.lagou.utils.ParameterMapping;
import com.lagou.utils.ParameterMappingTokenHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SimpleExecutor implements Executor {
    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException {
        // 1.加载驱动,获取连接
        final Connection connection = configuration.getDataSource().getConnection();

        // 2.创建预处理对象
        final String sql = mappedStatement.getSql();
        //

        final BoundSql boundSql = getBoundSql(sql);
        // 3.获取预处理对象
        connection.prepareStatement(boundSql.getSqlText());

        // 4.设置参数


        // 5.执行sql

        // 6.封装返回结果急

        return null;
    }


    private BoundSql getBoundSql(String sql) {
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);

        // 解析出来的sql
        final String parseSql = genericTokenParser.parse(sql);

        // #{}里面解析出来的参数名称
        final List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();

        return new BoundSql(parseSql, parameterMappings);

    }
}
