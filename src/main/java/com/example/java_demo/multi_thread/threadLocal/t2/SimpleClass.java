package com.example.java_demo.multi_thread.threadLocal.t2;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SimpleClass {
    private final int index;
    private Executor mExecutors = Executors.newSingleThreadExecutor();

    public SimpleClass(int index) {
        this.index = index;
    }

    public void runTask() {
        mExecutors.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("Index:" + index + " execute");
            }
        });
    }

}
