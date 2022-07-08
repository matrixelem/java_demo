package com.example.java_demo.file;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TimeAnalyze {
    public static void main(String[] args) throws Exception {
        String filePath = "";

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

        List<String> lines = Files.readAllLines(Paths.get(filePath));
        List<LocalTime> list = new ArrayList<>();
        for(String line: lines){
            String time = line.split(" ")[2];
            LocalTime localTime = LocalTime.parse(time, timeFormat);
            list.add(localTime);
        }
    }
}
