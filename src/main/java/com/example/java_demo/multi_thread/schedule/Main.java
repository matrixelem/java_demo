package com.example.java_demo.multi_thread.schedule;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        MotorService motorService = new MotorService();
        FaceService faceService = new FaceService();

        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i=0; i < 100000; i++){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    StructurizedDoc doc1 = new MotorDoc();
                    doc1.setId(UUID.randomUUID().toString());
                    motorService.save(doc1);

                    StructurizedDoc doc2 = new FaceDoc();
                    doc2.setId(UUID.randomUUID().toString());
                    faceService.save(doc2);
                }
            });
        }

    }
}
