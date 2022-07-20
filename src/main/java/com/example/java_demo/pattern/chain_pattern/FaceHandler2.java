package com.example.java_demo.pattern.chain_pattern;

public class FaceHandler2 extends AbstractDocHandler{
    @Override
    protected Doc handleDoc(Doc doc) {
        System.out.println("FaceHandler2 handle");
        StringBuilder sb = new StringBuilder(doc.getMsg());
        sb.append("handle2");
        doc.setMsg(sb.toString());
        return doc;
    }
}
