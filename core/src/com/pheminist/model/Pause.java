package com.pheminist.model;

public class Pause extends SimpleEvent<Pause>{

    private boolean paused;

    public Pause(boolean paused) {
        this.paused = paused;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPause(boolean paused) {
        this.paused = paused;
        publisher.fire(this);
    }
}