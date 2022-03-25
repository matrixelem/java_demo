package com.example.java_demo.scheduling;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;

@Slf4j
public class FaceService extends AbstractClickHouseService {

    public void insert() {
        insertOne("face:" + UUID.randomUUID().toString());
    }

    @Override
    protected void batchRawInsert(List<String> list) {
        try {
            Path path = Paths.get("D:\\var\\qu\\face.txt");
            Files.deleteIfExists(path);
            Files.createFile(path);
            list.forEach(item -> {
                String s = item + "\n";
                try {
                    Files.write(path, s.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
