package com.example.java_demo.pattern.chain_pattern;

public abstract class AbstractDocHandler implements IDocHandler {
    private IDocHandler next;

    protected abstract Doc handleDoc(Doc doc);

    public void setNext(IDocHandler next) {
        this.next = next;
    }

    @Override
    public Doc handle(Doc doc) {
        Doc doc1 = handleDoc(doc);
        if (next !=  null){
            return next.handle(doc1);
        }
        return doc1;
    }
}
