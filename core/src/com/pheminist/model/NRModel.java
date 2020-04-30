package com.pheminist.model;

import com.pheminist.model.MIDI.HFNote;
import com.pheminist.model.MIDI.HFNoteHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class NRModel {
    public final List<HFNote> screenNotes = new ArrayList<>();
    public final Model model;
    private HFNoteHandler hData;
    private boolean[] isNoteOns;
    private boolean[] isNoteAlives;

    NRModel(Model model) {
        this.model = model;
    }

    public void init() {
        hData = model.gethData();
        model.time.setTime(-2);
        isNoteOns = new boolean[hData.getnSoundes()];

        isNoteAlives = new boolean[hData.getnSoundes()];
        Arrays.fill(isNoteAlives, true);
    }

    public void update(float deltaTime) {
        if (model.pause.isPaused()) return;

        float secondInScreen = model.sps.getSps();
        float curTime = model.time.getTime();
        curTime = curTime + deltaTime * model.tempo.getTempo();
        model.time.setTime(curTime);

        while (hData.hasNext())
            if (hData.getNexTime() < curTime + secondInScreen) {
                HFNote hNote = hData.getNext();
                screenNotes.add(hNote);
            } else break;

        Iterator<HFNote> iterator = screenNotes.iterator();
        while (iterator.hasNext()) {
            HFNote hNote = iterator.next();
            int note = hNote.getNote();
            if (hNote.getTime() < curTime && !isNoteOns[note]) {
                isNoteOns[note] = true;
                if (isNoteAlives[note])
                    model.noteEvent.fireNoteEvent(note, hData.getTone(note), true);
            }
            if (hNote.getTime() + hNote.getDuration() < curTime) {
                isNoteOns[note] = false;
                model.noteEvent.fireNoteEvent(note, hData.getTone(note), false);

                iterator.remove();
            }
            if (!hData.hasNext() && screenNotes.isEmpty()) {
                model.pause.setPause(true);
                model.gethData().setIndexByTime(-2f);
                model.time.setTime(-2);
            }

        }
    }

    public HFNoteHandler gethData() {
        return hData;
    }

    public void setNoteAlive(int i, boolean isAlive) {
        isNoteAlives[i] = isAlive;
    }

    public void allNotesOffByEvents(){
        for (int i=0;i<isNoteOns.length;i++){
            if(isNoteOns[i]){
                isNoteOns[i]=false;
                model.noteEvent.fireNoteEvent(i,hData.getTone(i),false);
            }
        }
    }

    public String getDeadNotes() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < isNoteAlives.length; i++) {
            if (!isNoteAlives[i]) {
                str.append("_").append(i + 1);
            }
        }
        return str.toString();
    }
}
