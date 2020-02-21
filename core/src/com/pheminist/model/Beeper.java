package com.pheminist.model;

import com.pheminist.interfaces.IListener;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

public class Beeper implements IListener<NoteEvent> {
    private MidiChannel[] channels;
    private int shift;

    public Beeper(Model model){

        model.shift.getPublisher().addListener(new IListener<Shift>() {
            @Override
            public void on(Shift event) {
                Beeper.this.setShift(event.getShift());
            }
        });

        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            channels = synth.getChannels();
            channels[0].programChange(22);
//            synth.close();
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void allNotesOff() {
        channels[0].allNotesOff();
    }

    public void setShift(int shift) {
        allNotesOff();
        this.shift = shift;
    }

    @Override
    public void on(NoteEvent event) {
        int tone=event.getTone()+shift;
        if(event.isOn()) channels[0].noteOn(tone,80);
        else channels[0].noteOff(tone);
    }
}
