package com.pheminist.model.MIDI;

import java.util.Comparator;

import javax.sound.midi.MidiEvent;

public class EventWithTrack {
    MidiEvent midiEvent;
    int trackNumber;

    public EventWithTrack(MidiEvent midiEvent, int trackNumber) {
        this.midiEvent = midiEvent;
        this.trackNumber = trackNumber;
    }

    public MidiEvent getMidiEvent() {
        return midiEvent;
    }

    public void setMidiEvent(MidiEvent midiEvent) {
        this.midiEvent = midiEvent;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }
}
