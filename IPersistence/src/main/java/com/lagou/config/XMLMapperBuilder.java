package com.lagou.config;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XMLMapperBuilder {
    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }


    public void parse(InputStream inputStream) throws DocumentException, ClassNotFoundException {

        final Document document = new SAXReader().read(inputStream);

        // 获取文档根节点
        final Element rootElement = document.getRootElement();

        // 获取namespace
        final String namespace = rootElement.attributeValue("namespace");

        final List<Element> list = rootElement.selectNodes("//select");

        for (Element element : list) {
            final String id = element.attributeValue("id");
            final String resultType = element.attributeValue("resultType");
            final String parameterType = element.attributeValue("parameterType");
            final String sqlText = element.getTextTrim();

            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(Integer.valueOf(id));
            mappedStatement.setParamterType(Class.forName(parameterType));
            mappedStatement.setResultType(Class.forName(resultType));
            mappedStatement.setSql(sqlText);
            configuration.getMappedStatementMap().put(namespace + "." + id, mappedStatement);
        }

    }
}
