package com.pheminist.model.MIDI;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

public class OldEventProvider {
    private MidiEvent[] events;
    private Sequence sequence = null;

    public OldEventProvider(File file) {
        try {
            sequence = MidiSystem.getSequence(file);
        } catch (InvalidMidiDataException e) {
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Track[] tracks = Objects.requireNonNull(sequence).getTracks();

        int i = 0;
        for (Track track : tracks) i = i + track.size();
        events = new MidiEvent[i];

        int eventIndex = 0;
        for (Track track : tracks) {
            for (i = 0; i < track.size(); i++) {
                events[eventIndex] = track.get(i);
                eventIndex++;
            }
        }

        Arrays.sort(events, new Comparator<MidiEvent>() {
            @Override
            public int compare(MidiEvent event1, MidiEvent event2) {
                return Long.compare(event1.getTick(), event2.getTick());
            }
        });
    }

    public MidiEvent[] getEvents(){
        return events;
    }

    Sequence getSequence() {
        return sequence;
    }
}
