package com.lagou.config;


import com.lagou.io.Resources;
import com.lagou.pojo.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 *
 */
public class XMLConfigBuilder {

    private Configuration configuration;


    public XMLConfigBuilder() {
        this.configuration = new Configuration();
    }

    /**
     * 该方法就是使用dom4j对配置文件进行解析，封装成configuration对象
     *
     * @param inputStream
     * @return
     */
    public Configuration parseConfig(InputStream inputStream) throws DocumentException, PropertyVetoException, ClassNotFoundException {

        final Document document = new SAXReader().read(inputStream);
        // 获取根对象
        final Element rootElement = document.getRootElement();

        final List<Element> list = rootElement.selectNodes("//property");

        Properties properties = new Properties();
        list.forEach(element -> {
            final String name = element.attributeValue("name");
            final String value = element.attributeValue("value");
            properties.setProperty(name, value);
        });

        // 使用c3p0连接池
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(properties.getProperty("driverClass"));
        dataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        dataSource.setUser(properties.getProperty("user"));
        dataSource.setPassword(properties.getProperty("root"));

        configuration.setDataSource(dataSource);

        // 解析mapper.xml文件
        final List<Element> mapperList = rootElement.selectNodes("//mapper");
        // 创建xmlMapper解析对象
        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
        for (Element element : mapperList) {
            final String mapperPath = element.attributeValue("resource");
            final InputStream resourceAsSteam = Resources.getResourceAsSteam(mapperPath);
            xmlMapperBuilder.parse(resourceAsSteam);
        }

        return configuration;
    }
}
