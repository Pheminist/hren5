package com.pheminist.model.MIDI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static com.pheminist.model.MIDI.MEventReader.NOTE_OFF;
import static com.pheminist.model.MIDI.MEventReader.NOTE_ON;

public class HNoteProvider {
    private List<Note> notes = new ArrayList<>(); // all kinds of notes in the melody
    private HFNote[] hfNotesArray;
    private Note[] notesArray;

    public HNoteProvider(MidiData midiData) {
        TickToTime tickToTime = new TickToTime(midiData);

        List<ActiveNote> activeNotes = new ArrayList<>();
        List<HFNote> hfNotes = new ArrayList<>();

 //       System.out.println("HHHHHHH");
        for (Track track : midiData.getTracks()) {
//            System.out.println("TRACK+++++++++++++++++++");
            MEventReader mEventReader = new MEventReader(track);
            while (mEventReader.readNext()) {
                int status = mEventReader.getStatus();
                int noteNumber;
                switch (status) {
                    case NOTE_ON:
//                        System.out.printf("\n ON : %3d  %3d  %10d",
//                                mEventReader.getChannel(),mEventReader.getTone(),mEventReader.getCurTick());
                        noteNumber = getNoteNumber(mEventReader.getChannel(), mEventReader.getTone());
                        activeNotes.add(new ActiveNote(noteNumber,mEventReader.getCurTick()));
                        break;
                    case NOTE_OFF:

 //                       System.out.printf("\nOFF : %3d  %3d  %10d",
 //                               mEventReader.getChannel(),mEventReader.getTone(),mEventReader.getCurTick());
                        noteNumber = getNoteNumber(mEventReader.getChannel(), mEventReader.getTone());
                        long timeInTicks = 0;
                        int i;
                        boolean founded = false;
                        for (i = 0; i < activeNotes.size(); i++) {
                            ActiveNote an = activeNotes.get(i);
                            if (an.number == noteNumber) {
                                timeInTicks = an.time;
                                founded = true;
//                                System.out.println(" timeInTicks  " + timeInTicks);
                                break;
                            }
                        }
                        float startTime=tickToTime.tickToSecond(timeInTicks);
                        float endTime=tickToTime.tickToSecond(mEventReader.getCurTick());
                        hfNotes.add(new HFNote(noteNumber, startTime, endTime-startTime));


//                        if(activeNotes.size()==0) break;


                        if(founded)
                            activeNotes.remove(i);
//                        System.out.println("OFF");
                        break;
                }
            }
        }

        hfNotesArray = hfNotes.toArray(new HFNote[0]);
        Arrays.sort(hfNotesArray, new Comparator<HFNote>() {
            @Override
            public int compare(HFNote n1, HFNote n2) {
                return Float.compare(n1.getTime(), n2.getTime());
            }
        });

        notesArray = notes.toArray(new Note[0]);

        int[] temp = new int[notesArray.length];
        for (int j = 0; j < notesArray.length; j++) {
            int n = 0;
            for (Note note : notesArray) {
                if (note.channel < notesArray[j].channel) n++;
                else if (note.channel == notesArray[j].channel && note.tone < notesArray[j].tone)
                    n++;
            }
            temp[j] = n;
        }

        for (HFNote note : hfNotesArray) {
            note.setNote(temp[note.getNote()]);
        }

        Note[] tmp = notesArray.clone();

        for (int i = 0; i < notesArray.length; i++) {
            notesArray[temp[i]] = tmp[i];
        }

//        System.out.print("\n notesArray  ");
//        for (Note note : notesArray) System.out.printf("%3d", note.tone);
//        System.out.print("\n tempNotes   ");
//        for (Note note : tmp) System.out.printf("%3d", note.tone);
//        System.out.print("\n temp        ");
//        for (int i : temp) System.out.printf("%3d", i);

    }

    private int getNoteNumber(int channel, int tone) {
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            if (note.channel == channel && note.tone == tone) return i;
        }
        notes.add(new Note(channel, tone));
        return notes.size() - 1;
    }

    HFNote[] getHNotes() {
        return hfNotesArray;
    }

    Note[] getNotes() {
        return notesArray;
    }

    int numberOfSounds() {
        return notes.size();
    }
}
