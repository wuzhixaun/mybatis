package com.lagou.sqlSession;

import com.lagou.config.BoundSql;
import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import com.lagou.utils.GenericTokenParser;
import com.lagou.utils.ParameterMapping;
import com.lagou.utils.ParameterMappingTokenHandler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements Executor {
    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        // 1.加载驱动,获取连接
        final Connection connection = configuration.getDataSource().getConnection();

        // 2.创建预处理对象
        final String sql = mappedStatement.getSql();
        //

        final BoundSql boundSql = getBoundSql(sql);
        // 3.获取预处理对象
        final PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        // 4.设置参数
        final List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();

        final Class<?> paramterType = mappedStatement.getParamterType();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            // 获取参数对象
            final ParameterMapping parameterMapping = parameterMappingList.get(i);
            // 获取属性值
            final String name = parameterMapping.getContent();

            // 获取对应字段
            final Field field = paramterType.getDeclaredField(name);
            field.setAccessible(true);
            // 获取对应对象的字段属性值
            final Object value = field.get(params[0]);

            preparedStatement.setObject(i + 1, value);
        }

        // 5.执行sql
        final ResultSet resultSet = preparedStatement.executeQuery();


        // 6.封装返回结果急
        ArrayList<E> list = new ArrayList<>();
        final Class<?> resultType = mappedStatement.getResultType();
        while (resultSet.next()) {
            final E instance = (E)resultType.newInstance();
            // 获取元数据信息
            final ResultSetMetaData metaData = resultSet.getMetaData();

            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                final String columnName = metaData.getColumnName(i);

                final Object value = resultSet.getObject(columnName);


                PropertyDescriptor descriptor = new PropertyDescriptor(columnName,resultType);
                // 获取写入放入
                final Method writeMethod = descriptor.getWriteMethod();
                writeMethod.invoke(instance, value);
            }
            list.add(instance);

        }

        return list;
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
