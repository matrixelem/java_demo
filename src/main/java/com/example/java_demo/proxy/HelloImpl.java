package com.example.java_demo.proxy;

public class HelloImpl implements Hello{
    @Override
    public void say() {
        System.out.println("impl say");
    }
}
