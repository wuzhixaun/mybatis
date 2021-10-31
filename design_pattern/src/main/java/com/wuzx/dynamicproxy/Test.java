package com.wuzx.dynamicproxy;

public class Test {
    public static void main(String[] args) {

        final Person proxy = (Person) new JDKDynamicProxy(new Bob()).getProxy();

        proxy.doSomeThing();

    }
}



