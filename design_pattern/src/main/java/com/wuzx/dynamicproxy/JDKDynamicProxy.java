package com.wuzx.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKDynamicProxy implements InvocationHandler {


    private Person person;

    public JDKDynamicProxy(Person person) {
        this.person = person;
    }


    // 获取代理对象
    public Object getProxy() {
        return Proxy.newProxyInstance(person.getClass().getClassLoader(), person.getClass().getInterfaces(), this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("原方法增强");
        // 原方法执行
        method.invoke(person, args);
        return null;
    }
}
