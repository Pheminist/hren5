package com.pheminist.model.MIDI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;

public class HFNoteProvider {
    private List<Note> notes = new ArrayList<>();
    private List<HFNote> hfNotes = new ArrayList<>();
    private HFNote[] hfNotesArray;
    private Note[] notesArray;

    public HFNoteProvider(EventProvider eventProvider) {
        TickToTime tickToTime = new TickToTime(eventProvider.getSequence());
        MidiEvent[] events = eventProvider.getEvents();
        List<ActiveNote> activeNotes = new ArrayList<>();

        for (MidiEvent event : events) {
            if (!(event.getMessage() instanceof ShortMessage)) continue;
            ShortMessage message = (ShortMessage) event.getMessage();
            int command = message.getCommand();
            if (command == ShortMessage.NOTE_ON && message.getData2() != 0) {
                int noteNumber = getNoteNumber(message.getChannel(), message.getData1());
                activeNotes.add(new ActiveNote(noteNumber, event.getTick()));
                System.out.println("NOTE_ON "+event.getTick());

            } else if (command == ShortMessage.NOTE_OFF
                    || (command == ShortMessage.NOTE_ON && message.getData2() == 0)) {
                int noteNumber = getNoteNumber(message.getChannel(), message.getData1());

                long timeInTicks=0;
                int i;
                for (i=0;i<activeNotes.size();i++) {
                    ActiveNote an=activeNotes.get(i);
                    if(an.number==noteNumber) {
                        timeInTicks=an.time;
                        System.out.println(" timeInTicks  "+timeInTicks);
                        break;}
                }
//                long timeInTicks = activeNotes.get(noteNumber).time;
                long durationInTicks = event.getTick() - timeInTicks;
                hfNotes.add(new HFNote(noteNumber, tickToTime.tickToSecond(timeInTicks)
                        , tickToTime.tickToSecond(durationInTicks)));
                activeNotes.remove(i);
            }
        }

        hfNotesArray =hfNotes.toArray(new HFNote[0]);
        Arrays.sort(hfNotesArray, new Comparator<HFNote>() {
            @Override
            public int compare(HFNote n1, HFNote n2) {
                return Float.compare(n1.getTime(), n2.getTime());
            }
        });

        notesArray=notes.toArray(new Note[0]);

    }

    private int getNoteNumber(int channel, int tone) {
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            if (note.channel == channel && note.tone == tone) return i;
        }
        notes.add(new Note(channel, tone));
        return notes.size() - 1;
    }

    public HFNote[] getHNotes() {
        return hfNotesArray;
    }

    public Note[] getNotes() {
        return notesArray;
    }

    public int numberOfSounds(){return notes.size();}
}
