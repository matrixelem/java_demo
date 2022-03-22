package com.example.java_demo.merge_test;

import org.apache.commons.lang3.RandomUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FaceService extends AbstractClickHouseService {

    public static final Path path = Paths.get("D:\\var\\d.txt");

    public void add(StructureDoc doc) {
        insertOne(doc);
    }

    @Override
    protected void batchRawInsert(List<StructureDoc> list) {
        list.forEach(item -> {
            try {
                String s = item.getId() + "\n";
                Files.write(path, s.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.out.println("本次插入对象：" + list.size());
    }

    public static void main(String[] args) throws Exception {
        Files.deleteIfExists(path);
        Files.createFile(path);
        FaceService faceService = new FaceService();

        Executor executor = Executors.newFixedThreadPool(20);


        for (int i = 0; i < 200; i++) {
            TimeUnit.MILLISECONDS.sleep(RandomUtils.nextLong(300, 1500));
            int finalI = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    StructureDoc doc = new StructureDoc();
                    doc.setId(String.valueOf(finalI));
                    faceService.add(doc);
                }
            });
        }
    }
}
