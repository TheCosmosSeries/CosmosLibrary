package com.tcn.cosmoslibrary.system.primative;

public class ObjectHolder2<A extends Object, B extends Object> {

    private final A first;
    private final B second;
    
    public ObjectHolder2(A firstObjectIn, B secondObjectIn) {
        this.first = firstObjectIn;
        this.second = secondObjectIn;
    }
    
    public A getFirst() {
        return this.first;
    }
    
    public B getSecond() {
        return this.second;
    }

    @Override
    public String toString() {
        return "{ ObjectHolderTwo: [First: '" + this.first.toString() + "', Second: '" + this.second.toString() + "'] }";
    }
}
