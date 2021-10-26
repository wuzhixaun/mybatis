package com.lagou.sqlSession;

import com.lagou.pojo.Configuration;

import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) {

        SimpleExecutor simpleExecutor = new SimpleExecutor();
        return simpleExecutor.query(configuration, configuration.getMappedStatementMap().get(statementId), params);
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) {

        final List<Object> list = selectList(statementId, params);

        if (list.size() != 1) {
            throw new RuntimeException("查询结果不存在或者大于1");
        }


        return (T) list.get(0);
    }
}
