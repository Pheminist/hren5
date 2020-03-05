package com.pheminist.model;

public class Time extends SimpleEvent<Time> {
    private float time;

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
        publisher.fire(this);
    }
}
