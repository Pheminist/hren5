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

        int[] temp=new int[notesArray.length];
        for (int j=0;j<notesArray.length;j++){
            int n=0;
            for (int i=0;i<notesArray.length;i++){
                if(notesArray[i].channel<notesArray[j].channel) n++;
                else if(notesArray[i].channel==notesArray[j].channel && notesArray[i].tone<notesArray[j].tone) n++;
            }
            temp[j]=n;
        }

        for(HFNote note:hfNotesArray){
            note.setNote(temp[note.getNote()]);
        }

        Note[] tmp = notesArray.clone();

//        tmp[1]=new Note(100,200);
//        System.out.println(" ============= channel  tone "+notesArray[1].channel+"  "+notesArray[1].tone);

        for (int i = 0; i < notesArray.length; i++) {
            notesArray[temp[i]]=tmp[i];
        }

        System.out.printf("\n notesArray  ");
        for(Note note:notesArray) System.out.printf("%3d",note.tone);
        System.out.printf("\n tempNotes   ");
        for(Note note:tmp) System.out.printf("%3d",note.tone);
        System.out.printf("\n temp        ");
        for(int i:temp) System.out.printf("%3d",i);

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
