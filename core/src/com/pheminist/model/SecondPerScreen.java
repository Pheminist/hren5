package com.pheminist.model;

public class SecondPerScreen extends SimpleEvent<SecondPerScreen>{
    private float sps =2;

    public float getSps() {
        return sps;
    }

    public void setSps(float sps) {
        if(sps <0.99f || sps >6.01f) return;
        this.sps = sps;
        publisher.fire(this);
    }
}
