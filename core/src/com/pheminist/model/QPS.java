package com.pheminist.model;

public class QPS extends SimpleEvent<QPS>{
    private float qps=4;

    public float getQps() {
        return qps;
    }

    public void setQps(float qps) {
        if(qps<1.5 || qps>15.5) return;
        this.qps = qps;
        publisher.fire(this);
    }
}
