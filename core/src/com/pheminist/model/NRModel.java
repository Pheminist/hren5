package com.pheminist.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.pheminist.model.MIDI.HData;
import com.pheminist.model.MIDI.HNote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class NRModel {
    public final List<HNote> screenNotes = new ArrayList<>();
    public final Model model;
    public final Tick tick = new Tick();
    private HData hData;
    //    private float curTick;
    private boolean paused;
    private boolean[] isNoteOns;
    private boolean[] isNoteAlives;

    public NRModel(Model model) {
        this.model = model;
    }

    public void init() {
        hData = model.gethData();
        isNoteOns = new boolean[hData.getnTones()];

        isNoteAlives = new boolean[hData.getnTones()];
        Arrays.fill(isNoteAlives, true);

//        quarterInScreen = model.qps.getQps();
//        ticksInScreen = quarterInScreen * hData.getPpqn();
    }

    public void update(float deltaTime) {
        float quarterInScreen = model.qps.getQps();
        float ticksInScreen = quarterInScreen * hData.getPpqn();
        float curTick=tick.getTick();
        if (!paused) {
            curTick = curTick + deltaTime * 0.001f * hData.getTemp() * model.tempo.getTempo();
            tick.setTick(curTick);
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
            if (hData.getNexTime() < curTick + ticksInScreen) {
                HNote hNote = hData.getNext();
                screenNotes.add(hNote);
            } else break;

        Iterator<HNote> iterator = screenNotes.iterator();
        while (iterator.hasNext()) {
            HNote hNote = iterator.next();
            int note = hNote.getNote();
            if (hNote.getTime() < curTick && !isNoteOns[note]) {
                isNoteOns[note] = true;
                if (isNoteAlives[note])
                    model.noteEvent.fireNoteEvent(note, hData.getTones()[note], true);
            }
            if (hNote.getTime() + hNote.getDuration() < curTick) {
                isNoteOns[note] = false;
                model.noteEvent.fireNoteEvent(note, hData.getTones()[note], false);

                iterator.remove();
            }
        }
    }

    public HData gethData() {
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
}
