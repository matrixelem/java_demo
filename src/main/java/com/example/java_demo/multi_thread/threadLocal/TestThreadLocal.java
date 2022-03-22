package com.example.java_demo.multi_thread.threadLocal;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.NamedThreadLocal;

import java.util.List;

public class TestThreadLocal {
    private static final ThreadLocal<List<Long>> threadLocal = new NamedThreadLocal<>("TestThreadLocal123");

    public static void set(List<Long> personIdList) {
        if (CollectionUtils.isNotEmpty(threadLocal.get())){
            threadLocal.get().addAll(personIdList);
        } else {
            threadLocal.set(personIdList);
        }
    }

    public static List<Long> get() {
        return threadLocal.get();
    }
}
