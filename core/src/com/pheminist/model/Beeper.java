package com.pheminist.model;

import com.pheminist.interfaces.IHorner;
import com.pheminist.interfaces.IListener;

public class Beeper implements IListener<NoteEvent> {
    public final IListener<Shift> shiftListener;
    private IHorner horner;
    private Model model;
    private int shift;

    Beeper(Model model) {
        horner = model.horner;
        this.model = model;
        shiftListener = new IListener<Shift>() {
            @Override
            public void on(Shift event) {
                setShift(event.getShift());
            }
        };
    }

    public void allNotesOff() {
        horner.allNotesOff();
    }

    public void allNotesOn() {
        if (!model.sound) return;
        boolean[] ons = model.nrModel.getIsNoteOns();
        boolean[] alives = model.nrModel.getIsNoteAlives();
        for (int i = 0; i < ons.length; i++) {
            if (ons[i] & alives[i]) {
                horner.noteOn(0, model.nrModel.gethData().getTone(i) + shift);
            }
        }
    }

    private void setShift(int shift) {
        allNotesOff();
        this.shift = shift;
        allNotesOn();
    }

    @Override
    public void on(NoteEvent event) {
        int tone = event.getTone() + shift;
        if (event.isOn()) horner.noteOn(0, tone);
        else horner.noteOff(0, tone);
    }

    void dispose() {
        horner.dispose();
    }
}
