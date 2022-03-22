package com.example.java_demo.multi_thread.threadLocal.t2;

public class ThreadPoolLife {

    public static void main(String[] args) {
        for (int i=0; i< 10; i++){
            new SimpleClass(i).runTask();
        }
        System.gc();
    }
}
