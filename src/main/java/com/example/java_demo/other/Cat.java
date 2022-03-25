package com.example.java_demo.other;

import java.util.ArrayList;
import java.util.List;

public class Cat extends Animal{
    public void insert(String s) {
        insertOne("cat:" + s);
    }


    private List<? extends Animal> data = new ArrayList<>();

    public <T extends Animal> void in(T doc) {

    }
}
