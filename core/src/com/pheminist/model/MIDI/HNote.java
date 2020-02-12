package com.pheminist.model.MIDI;

public class HNote implements Comparable<HNote>{
    private int note;
    private int time;
    private int duration;

    public HNote(int note, int time, int duration) {
        this.note = note;
        this.time = time;
        this.duration = duration;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public int compareTo(HNote note) {
        return (Integer.compare(this.getTime(), note.getTime()));
    }
}
