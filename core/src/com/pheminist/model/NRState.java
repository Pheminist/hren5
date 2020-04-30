//package com.pheminist.model;
//
//public class NRState extends SimpleEvent<NRState>{
//
//    public final static int RUN=1;
//    public final static int PAUSED=2;
//    public final static int FINISH=3;
//
//    private int state;
//
//    public NRState(int state) {
//        this.state = state;
//    }
//
//    public int getState() {
//        return state;
//    }
//
//    public void setState(int state) {
//        this.state = state;
//        publisher.fire(this);
//    }
//}