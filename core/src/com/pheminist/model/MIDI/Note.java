package com.pheminist.model.MIDI;

public class Note {
    protected int channel;
    protected int tone;

    public Note(int channel, int tone) {
        this.channel = channel;
        this.tone = tone;
    }
}
