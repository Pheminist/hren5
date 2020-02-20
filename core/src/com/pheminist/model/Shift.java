package com.pheminist.model;

public class Shift extends SimpleEvent<Shift>{
    public final static int minShift = -12;
    public final static int maxShift = +12;

    private int shift;

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        if(shift<minShift || shift>maxShift ) return;
        this.shift=shift;
        publisher.fire(this);
    }
}
