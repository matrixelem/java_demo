package com.example.java_demo.excep;

public class ExceptionTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i =0; i < 1000000; i++){
            try {
                throw new CustomException();
            } catch (CustomException e) {

            }

        }
        System.out.println("time====" + (System.currentTimeMillis() - start));
    }
}
