package com.example.java_demo.scheduling;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class AbstractClickHouseService {

    //最大插入条数
    private static final int MAX_RECORD_NUM = 1000;

    private final List<String> data = new ArrayList<>(MAX_RECORD_NUM);

    // 子类单插调用
    public void insertOne(String doc) {
        log.info("ck insert one, id:{}", doc);
        synchronized (data){
            data.add(doc);
            if (data.size() > MAX_RECORD_NUM){
                batchRawInsert(data);
                data.clear();
            }
        }
    }

    /**
     * 每隔1s执行一次
     */
    @Scheduled(cron = "*/1 * * * * ?")
    public void timerBatchInsert() {
        log.info("ck timer bulk insert execute");
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
    protected abstract void batchRawInsert(List<String> list);

}
