package com.pheminist.model.MIDI;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

public class EventProvider {
    private int index;
//    private EventWithTrack[] events;
    private MidiEvent[] events;
    private Sequence sequence = null;

    public EventProvider(File file) {
        try {
            sequence = MidiSystem.getSequence(file);
        } catch (InvalidMidiDataException e) {
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Track[] tracks = sequence.getTracks();

        int i = 0;
        for (Track track : tracks) i = i + track.size();
//        events = new EventWithTrack[i];
        events = new MidiEvent[i];

//        int trackNumber = 0;
//        int eventIndex = 0;
//        for (Track track : tracks) {
//            for (i = 0; i < track.size(); i++) {
//                events[eventIndex] = new EventWithTrack(track.get(i), trackNumber);
//                eventIndex++;
//            }
//            trackNumber++;
//        }
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
                if(event1.getTick()<event2.getTick()) return -1;
                if(event1.getTick()>event2.getTick()) return 1;
                return 0;
            }
        });
    }

    public EventWithTrack getNext() {
        return null;
    }

    public MidiEvent[] getEvents(){
        return events;
    }

    public Sequence getSequence() {
        return sequence;
    }
}
