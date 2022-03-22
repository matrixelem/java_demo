package com.example.java_demo.merge_test;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public abstract class AbstractClickHouseService {

    private List<StructureDoc> data;


    public AbstractClickHouseService() {
        data = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        System.out.println("定时任务执行");
                        batchInsert();
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    // 子类单插调用
    public void insertOne(StructureDoc doc) {
        synchronized (data){
            data.add(doc);
            if (data.size() > 5){
                batchRawInsert(data);
                data.clear();
            }
        }
    }
    public void batchInsert() {
        synchronized (data){
            if (data.isEmpty()){
                return;
            }
            batchRawInsert(data);
            data.clear();
        }
    }

    /**
     * 子类实现， 真正插入数据库的方法
     * @param list
     */
    protected abstract void batchRawInsert(List<StructureDoc> list);


}
