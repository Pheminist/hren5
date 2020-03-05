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
    private boolean paused;
    private boolean[] isNoteOns;
    private boolean[] isNoteAlives;

    public NRModel(Model model) {
        this.model = model;
    }

    public void init() {
        hData = model.gethData();
//        time.setTime(- model.qps.getQps()*hData.getPpqn());
        time.setTime(- 2);
        isNoteOns = new boolean[hData.getnSoundes()];

        isNoteAlives = new boolean[hData.getnSoundes()];
        Arrays.fill(isNoteAlives, true);

//        quarterInScreen = model.qps.getQps();
//        ticksInScreen = quarterInScreen * hData.getPpqn();
    }

    public void update(float deltaTime) {
//        float quarterInScreen = model.qps.getQps();
//        float ticksInScreen = quarterInScreen * hData.getPpqn();
        float secondInScreen =2;
        float curTick= time.getTime();
        if (!paused) {
            curTick = curTick + deltaTime * model.tempo.getTempo();
            time.setTime(curTick);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyPressed(Input.Keys.BACK)) { // get back to menu ...
//            beeper.allNotesOff();
//            parent.changeScreen(Controller.MENU);
            return;
        }

        if (!hData.hasNext() && screenNotes.isEmpty()) {
//            parent.changeScreen(Controller.MENU);
//            return;
        }

        while (hData.hasNext())
            if (hData.getNexTime() < curTick + secondInScreen) {
                HFNote hNote = hData.getNext();
                screenNotes.add(hNote);
            } else break;

        Iterator<HFNote> iterator = screenNotes.iterator();
        while (iterator.hasNext()) {
            HFNote hNote = iterator.next();
            int note = hNote.getNote();
            if (hNote.getTime() < curTick && !isNoteOns[note]) {
                isNoteOns[note] = true;
                if (isNoteAlives[note])
                    model.noteEvent.fireNoteEvent(note, hData.getTone(note), true);
            }
            if (hNote.getTime() + hNote.getDuration() < curTick) {
                isNoteOns[note] = false;
                model.noteEvent.fireNoteEvent(note, hData.getTone(note), false);

                iterator.remove();
            }
        }
    }

    public HFNoteHandler gethData() {
        return hData;
    }

//    public float getCurTick() {
//        return curTick;
//    }

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
}
