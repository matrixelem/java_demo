package com.example.java_demo.multi_thread;

import java.time.LocalDateTime;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        for (int i=0; i < 10; i++){
            new Thread(new Worker(semaphore), "thread" + i).start();
        }
    }

    static class Worker implements Runnable {
        private String name;
        private Semaphore semaphore;

        public Worker(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                log("waiting for permit");
                semaphore.acquire();
                log("acquired permit");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
                log("acquire release");
            }
        }

        private void log(String msg){
            System.out.println(Thread.currentThread().getName() + " " + LocalDateTime.now() + " " + msg);
        }
    }
}
