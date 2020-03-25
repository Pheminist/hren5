package com.pheminist.model;

import com.pheminist.interfaces.IHorner;
import com.pheminist.interfaces.IListener;

public class Beeper implements IListener<NoteEvent> {
    private IHorner horner;
    private int shift;
    public final IListener<Shift> shiftListener;

    Beeper(Model model) {
        horner=model.horner;
        shiftListener=new IListener<Shift>() {
            @Override
            public void on(Shift event) {
                setShift(event.getShift());
            }
        };
    }

    public void allNotesOff() {
        horner.allNotesOff();
    }

    private void setShift(int shift) {
        allNotesOff();
        this.shift = shift;
    }

    @Override
    public void on(NoteEvent event) {
        int tone = event.getTone() + shift;
        if (event.isOn()) horner.noteOn(0,tone);
        else horner.noteOff(0,tone);
    }

    void dispose() {
        horner.dispose();
    }
}
