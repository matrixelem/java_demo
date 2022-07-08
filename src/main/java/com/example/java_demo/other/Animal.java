package com.example.java_demo.other;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import org.apache.commons.lang3.RandomUtils;
import org.apache.poi.hssf.record.common.FutureRecord;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

public abstract class Animal {
    private final List<String> data = new ArrayList<>();

    protected abstract void insert(String s);


    public static void main(String[] args) {
        String s = "sdsd;;";

        System.out.println(Arrays.asList(s.split(";")));
    }
}
