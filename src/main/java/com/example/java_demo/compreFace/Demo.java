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
                        .addHeader("x-api-key", "604ca66c-c159-42d2-a409-5d7e24c002d3")
                        .url("http://10.171.2.180:8000/api/v1/recognition/recognize")
                        .post(requestBody)
                        .build();
                long s1 = System.currentTimeMillis();
                Response response = client.newCall(request).execute();
                long s2 = System.currentTimeMillis();
                System.out.println("time:" +(s2-s1));
                if (response.isSuccessful()){
                    Body body = JSON.parseObject(response.body().string(), Body.class);
                    System.out.println(path.getFileName() + ":" + body.subjects);
                }
                return FileVisitResult.CONTINUE;
            }
        });

    }

    @Data
    static class Body {
        private List<String> subjects;
    }
}
