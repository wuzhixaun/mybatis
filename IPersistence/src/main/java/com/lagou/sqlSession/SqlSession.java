package com.lagou.sqlSession;


import java.util.List;

/**
 * 定义数据库交互CRUD方法
 */
public interface SqlSession {

    /**
     * 查询列表
     */
    public <E> List<E> selectList(String statementId,Object... params);

    /**
     * 根据条件查询单个
     */
    public <T> T selectOne(String statementId, Object... params);
}
