package com.example.java_demo.multi_thread;

import java.util.concurrent.TimeUnit;

/**
 * 守护线程测试
 */
public class DaemonThread {

    public static void main(String[] args) {
        String s = "";
        String pattern = "[0-1][0-9]-[0-3][0-9][0-5][0-9]:[0-5][0-9]";


    }

    public static void t1() {
        System.out.println("主线程执行");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("守护线程执行结束");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.setDaemon(true);
        t.start();

        System.out.println("主线程执行完毕");
    }

    public void t2() {
        
    }

}
