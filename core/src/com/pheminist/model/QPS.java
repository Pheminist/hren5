package com.pheminist.model;

public class QPS extends SimpleEvent<QPS>{
    private float qps=4;

    public float getQps() {
        return qps;
    }

    public void setQps(float qps) {
        this.qps = qps;
        publisher.fire(this);
    }
}
