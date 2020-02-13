package com.pheminist.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.pheminist.model.MIDI.HData;
import com.pheminist.model.MIDI.HNote;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NRModel {
    public final List<HNote> screenNotes = new ArrayList<>();
    public final Model model;
    private HData hData;
    private float quarterInScreen;
    private float ticksInScreen;
    private float curTick;
    private boolean paused;
    private boolean isNoteOns[];

    public NRModel(Model model) {
        this.model = model;
    }

    public void init(){
        hData = model.gethData();
        isNoteOns = new boolean[hData.getnTones()];
        quarterInScreen = model.qps.getQps();
        ticksInScreen = quarterInScreen * hData.getPpqn();//*parent.getPreferences().getTempVolume();
    }

    public void update(float deltatime) {
//        if(Gdx.input.justTouched()) paused=!paused;
        if (!paused)
//        curTick = curTick + Gdx.graphics.getDeltaTime() * 0.001f * hData.getTemp() * model.tempo.getTempo();
        curTick = curTick + deltatime * 0.001f * hData.getTemp() * model.tempo.getTempo();
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
            if (hData.getNexTime() < curTick + ticksInScreen) {
                HNote hNote = hData.getNext();
                screenNotes.add(hNote);
            } else break;

        Iterator<HNote> iterator = screenNotes.iterator();
        while (iterator.hasNext()) {
            HNote hNote = iterator.next();
            if (hNote.getTime() < curTick && !isNoteOns[hNote.getNote()]) {
                isNoteOns[hNote.getNote()] = true;
//                notifyNoteListeners(hNote.getNote(), hData.getTones()[hNote.getNote()], true);
                model.noteEvent.fireNoteEvent(hNote.getNote(), hData.getTones()[hNote.getNote()], true);
            }
            if (hNote.getTime() + hNote.getDuration() < curTick) {
                isNoteOns[hNote.getNote()] = false;
//                notifyNoteListeners(hNote.getNote(), hData.getTones()[hNote.getNote()], false);
                model.noteEvent.fireNoteEvent(hNote.getNote(), hData.getTones()[hNote.getNote()], false);

                iterator.remove();
            }
        }
    }

    public HData gethData() {
        return hData;
    }

    public float getCurTick() {
        return curTick;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
