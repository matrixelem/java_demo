package com.example.java_demo.pattern.chain_pattern;

public class FaceHandler1 extends AbstractDocHandler {
    @Override
    protected Doc handleDoc(Doc doc) {
        System.out.println("FaceHandler1 handle");
        StringBuilder sb = new StringBuilder(doc.getMsg());
        sb.append("handle1");
        doc.setMsg(sb.toString());
        return doc;
    }
}
