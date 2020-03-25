package com.pheminist.model;

import com.pheminist.interfaces.IHorner;
import com.pheminist.interfaces.IListener;

public class Beeper implements IListener<NoteEvent> {
    private IHorner horner;
    private int shift;

    Beeper(Model model) {
        horner=model.horner;

        model.shift.getPublisher().addListener(new IListener<Shift>() {
            @Override
            public void on(Shift event) {
                Beeper.this.setShift(event.getShift());
            }
        });

//        try {
//            synth = MidiSystem.getSynthesizer();
//            synth.open();
//            channels = synth.getChannels();
//            channels[0].programChange(22);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
