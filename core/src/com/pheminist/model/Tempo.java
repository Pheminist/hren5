package com.pheminist.model;

public class Tempo extends SimpleEvent<Tempo>{
    private float tempo=1;

    public float getTempo() {
        return tempo;
    }

    public void setTempo(float tempo) {
        this.tempo = tempo;
        publisher.fire(this);
    }
}
