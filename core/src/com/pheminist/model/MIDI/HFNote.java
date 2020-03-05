package com.pheminist.model.MIDI;

public class HFNote {
    private int note;
    private float time;
    private float duration;

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public float getTime() {
        return time;
    }

    public float getDuration() {
        return duration;
    }


    HFNote(int note, float time, float duration) {
        this.note = note;
        this.time = time;
        this.duration = duration;
    }
}
