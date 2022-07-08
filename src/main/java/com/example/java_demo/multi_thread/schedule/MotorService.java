package com.example.java_demo.multi_thread.schedule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class MotorService extends AbstractClickHouseService{

    private static final Path p = Paths.get("motor.db");

    public void save(StructurizedDoc doc) {
        insertOne(doc);
    }

    @Override
    protected void batchRawInsert(List<StructurizedDoc> list) {
        try {
            Files.deleteIfExists(p);
            Files.createFile(p);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<String> sd = new ArrayList<>();
        list.forEach(doc -> {
            sd.add("motor:" + doc.getId());
        });
        try {
            Files.write(p, sd, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
