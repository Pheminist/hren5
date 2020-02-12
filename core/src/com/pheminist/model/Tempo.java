package com.pheminist.model;

public class Tempo extends SimpleEvent<Tempo>{
    public final static float minTempo = 0.4f;
    public final static float maxTempo = 2f;

    private float tempo=1;

    public float getTempo() {
        return tempo;
    }

    public void setTempo(float tempo) {
        if(tempo<minTempo-0.001f || tempo>maxTempo+0.001f) return;
        this.tempo = tempo;
        publisher.fire(this);
    }
}
