package com.pheminist.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.pheminist.model.MIDI.HFNote;
import com.pheminist.model.MIDI.HFNoteHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class NRModel {
    public final List<HFNote> screenNotes = new ArrayList<>();
    public final Model model;
    public final Time time = new Time();
    private HFNoteHandler hData;
    private boolean paused=true;
    private boolean[] isNoteOns;
    private boolean[] isNoteAlives;

    NRModel(Model model) {
        this.model = model;
    }

    public void init() {
        hData = model.gethData();
        time.setTime(- 2);
        isNoteOns = new boolean[hData.getnSoundes()];

        isNoteAlives = new boolean[hData.getnSoundes()];
        Arrays.fill(isNoteAlives, true);
    }

    public void update(float deltaTime) {
        if(paused) return;

        float secondInScreen =model.sps.getSps();
        float curTime= time.getTime();
//        if (!paused) {
            curTime = curTime + deltaTime * model.tempo.getTempo();
            time.setTime(curTime);
//        }

        if (!hData.hasNext() && screenNotes.isEmpty()) {
            setPaused(true);
            model.nrState.setState(NRState.PAUSED);

            model.gethData().setIndexByTime(-2f);
            model.nrModel.time.setTime(-2);
        }

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
        }
    }

    public HFNoteHandler gethData() {
        return hData;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
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

    public String getDeadNotes(){
        String str="";
        for(int i=0;i<isNoteAlives.length;i++){
            if(!isNoteAlives[i] ){
                str=str+"_"+ (i+1);
            }
        }
        return str;
    }
}
