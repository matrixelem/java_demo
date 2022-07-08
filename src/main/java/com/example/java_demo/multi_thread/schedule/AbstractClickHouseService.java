package com.example.java_demo.multi_thread.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class AbstractClickHouseService {

    private Thread t;

    public AbstractClickHouseService() {
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    log.info("ck timer bulk insert execute");
                    synchronized (data){
                        if (data.isEmpty()){
                            return;
                        }
                        batchRawInsert(data);
                        data.clear();
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }

    //最大插入条数
    private static final int MAX_RECORD_NUM = 1000;

    private final List<StructurizedDoc> data = new ArrayList<>(MAX_RECORD_NUM);

    // 子类单插调用
    public void insertOne(StructurizedDoc doc) {
        log.info("ck insert one, id:{}", doc.getId());
        synchronized (data){
            data.add(doc);
            if (data.size() > MAX_RECORD_NUM){
                log.info("max len insert");
                batchRawInsert(data);
                data.clear();
            }
        }
    }

    /**
//     * 每隔1s执行一次
//     */
//    @Scheduled(cron = "*/1 * * * * ?")
//    public void timerBatchInsert() {
//        log.info("ck timer bulk insert execute");
//        synchronized (data){
//            if (data.isEmpty()){
//                return;
//            }
//            batchRawInsert(data);
//            data.clear();
//        }
//    }

    /**
     * 子类实现， 真正插入数据库的方法
     * @param list
     */
    protected abstract void batchRawInsert(List<StructurizedDoc> list);

}
