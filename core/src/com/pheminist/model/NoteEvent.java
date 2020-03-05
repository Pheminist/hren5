package com.pheminist.model;

public class NoteEvent extends SimpleEvent<NoteEvent>{
    private int note;
    private int tone;
    private boolean isOn;

    void fireNoteEvent(int note, int tone, boolean isOn){
        this.note=note;
        this.tone=tone;
        this.isOn=isOn;
        publisher.fire(this);
    }

    public int getNote() {
        return note;
    }

    int getTone() {
        return tone;
    }

    public boolean isOn() {
        return isOn;
    }

}
