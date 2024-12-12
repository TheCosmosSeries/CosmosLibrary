package com.tcn.cosmoslibrary.system.primative;

public class ObjectHolder3<A extends Object, B extends Object, C extends Object> {

    private final A first;
    private final B second;
    private final C third;
    
    public ObjectHolder3(A firstObjectIn, B secondObjectIn, C thirdObjectIn) {
        this.first = firstObjectIn;
        this.second = secondObjectIn;
        this.third = thirdObjectIn;
    }
    
    public A getFirst() {
        return this.first;
    }
    
    public B getSecond() {
        return this.second;
    }

    public C getThird() {
        return this.third;
    }
    
    @Override
    public String toString() {
        return "{ ObjectHolderThree: [First: '" + this.first.toString() + "', Second: '" + this.second.toString() + "', Third: '" + third.toString() + "'] }";
    }
}
