package com.pheminist.model;

import com.pheminist.interfaces.Publisher;

public class NoteEvent {
    private final Publisher<NoteEvent> noteEventPublisher = new SimplePublisher<>();
    private int note;
    private int tone;
    private boolean isOn;

    public void fireNoteEvent(int note,int tone,boolean isOn){
        this.note=note;
        this.tone=tone;
        this.isOn=isOn;
        noteEventPublisher.fire(this);
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getTone() {
        return tone;
    }

    public void setTone(int tone) {
        this.tone = tone;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public Publisher<NoteEvent> getNoteEventPublisher() {
        return noteEventPublisher;
    }

}
