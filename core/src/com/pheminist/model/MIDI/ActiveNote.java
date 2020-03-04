package com.pheminist.model.MIDI;

public class ActiveNote {
    int number;
    long time;
    long duration;

    public ActiveNote(int number, long time) {
        this.number = number;
        this.time = time;
    }
}
