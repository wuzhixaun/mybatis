package com.lagou.sqlSession;

import com.lagou.pojo.Configuration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws Exception {

        SimpleExecutor simpleExecutor = new SimpleExecutor();
        return simpleExecutor.query(configuration, configuration.getMappedStatementMap().get(statementId), params);
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws Exception {

        final List<Object> list = selectList(statementId, params);

        if (list.size() != 1) {
            throw new RuntimeException("查询结果不存在或者大于1");
        }

        return (T) list.get(0);
    }

    @Override
    public <T> T getMappper(Class<?> mapperClass) {
        Object proxyInstance = Proxy.newProxyInstance(mapperClass.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                // 准备参数 1：statmentid :sql语句的唯一标识：namespace.id= 接口全限定名.方法名
                // 方法名：findAll
                final String methodName = method.getName();
                final String className = method.getDeclaringClass().getName();
                String statementId = className + "." + methodName;

                // 准备参数2：params:args
                // 获取被调用方法的返回值类型
                Type genericReturnType = method.getGenericReturnType();
                // 判断是否进行了 泛型类型参数化
                if (genericReturnType instanceof ParameterizedType) {
                    List<Object> objects = selectList(statementId, args);
                    return objects;
                }

                return selectOne(statementId, args);
            }
        });
        return (T) proxyInstance;
    }
}
