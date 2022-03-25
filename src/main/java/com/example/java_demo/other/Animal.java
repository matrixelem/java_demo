package com.example.java_demo.other;

import org.apache.commons.lang3.RandomUtils;
import org.apache.poi.hssf.record.common.FutureRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

public abstract class Animal {
    private final List<String> data = new ArrayList<>();


    public void insertOne(String s){
        data.add(s);
    }

    public static void main(String[] args) {
        Cat cat = new Cat();
        Dog dog = new Dog();


        ExecutorService service = Executors.newFixedThreadPool(4);

        Future f = service.submit(() -> {
            for (int i=0; i < 10; i++){
                cat.insert(UUID.randomUUID().toString());
                try {
                    TimeUnit.MILLISECONDS.sleep(RandomUtils.nextLong(100, 500));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Future f2 = service.submit(() -> {
            for (int i=0; i < 10; i++){
                dog.insert(UUID.randomUUID().toString());
                try {
                    TimeUnit.MILLISECONDS.sleep(RandomUtils.nextLong(100, 500));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            f.get();
            f2.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("dfdf");

    }
}
