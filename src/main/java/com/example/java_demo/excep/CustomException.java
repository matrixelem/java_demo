package com.example.java_demo.excep;

public class CustomException extends RuntimeException{

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
