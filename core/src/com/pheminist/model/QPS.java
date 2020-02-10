package com.pheminist.model;

import com.pheminist.interfaces.Publisher;

public class QPS {
    private float qps=4;

    public float getQps() {
        return qps;
    }

    public void setQps(float qps) {
        this.qps = qps;
        QPSChanged.fire(this);
    }

    private final Publisher<QPS> QPSChanged = new SimplePublisher<>();
    public Publisher<QPS> onQPSChanged() {
        return QPSChanged;
    }

}
