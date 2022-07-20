package com.example.java_demo.compreFace;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class Demo {
    public static void main(String[] args) throws Exception {
        run();
    }


    public static void run() throws Exception {
        OkHttpClient client = new OkHttpClient();

        Path p = Paths.get("C:\\Users\\huangwei\\Pictures\\27人底库and视频");

        Files.walkFileTree(p, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                File file = path.toFile();
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file))
                        .build();
                Request request = new Request.Builder()
                        .addHeader("x-api-key", "870a4dcb-ed91-4aa9-a3ad-c0fe987bc94f")
                            .url("http://10.171.2.180:8000/api/v1/recognition/recognize")
                        .post(requestBody)
                        .build();
                long s1 = System.currentTimeMillis();
                Response response = client.newCall(request).execute();
                long s2 = System.currentTimeMillis();
                if (response.isSuccessful()){
                    System.out.println(response.body().string());
                }
                return FileVisitResult.CONTINUE;
            }
        });

    }

    @Data
    static class Body {
        private Result result;
    }

    @Data
    static class Result {
        private Box box;
        private List<Subject> subjects;
    }

    @Data
    static class Box {
        private double probability;
        private int x_max;
        private int y_max;
        private int x_min;
        private int y_min;
    }

    @Data
    static class Subject {
        private String subject;
        private double similarity;
    }


}
