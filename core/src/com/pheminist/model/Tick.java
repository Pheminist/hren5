package com.pheminist.model;

public class Tick extends SimpleEvent<Tick> {
    private float tick;

    public float getTick() {
        return tick;
    }

    public void setTick(float tick) {
        this.tick = tick;
        publisher.fire(this);
    }
}
