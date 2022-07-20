package com.example.java_demo.pattern.chain_pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * 责任链模式
 */
public class DocHandlerFactory {


    public static AbstractDocHandler getFaceHandler(){
        FaceHandler1 handler1 = new FaceHandler1();
        FaceHandler2 handler2 = new FaceHandler2();
        handler1.setNext(handler2);

        return handler1;
    }

    public static void main(String[] args) {
        Doc doc = new Doc();
        getFaceHandler().handle(doc);
        System.out.println("result========" + doc.getMsg());
    }
}
