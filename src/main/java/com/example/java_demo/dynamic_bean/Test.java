package com.example.java_demo.dynamic_bean;

public class Test {

    public static void main(String[] args) {
        TimePeriod period1 = new TimePeriod();
        period1.setStart(10000);
        period1.setEnd(20000);

        TimePeriod period2 = new TimePeriod();
        period2.setStart(10000);
        period2.setEnd(20000);

        System.out.println(period1.equals(period2));
    }
}
