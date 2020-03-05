package com.pheminist.model;

import com.pheminist.interfaces.IListener;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

public class Beeper implements IListener<NoteEvent> {
    private Synthesizer synth;
    private MidiChannel[] channels;
    private int shift;

    Beeper(Model model) {

        model.shift.getPublisher().addListener(new IListener<Shift>() {
            @Override
            public void on(Shift event) {
                Beeper.this.setShift(event.getShift());
            }
        });

        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channels = synth.getChannels();
            channels[0].programChange(22);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void allNotesOff() {
        channels[0].allNotesOff();
    }

    private void setShift(int shift) {
        allNotesOff();
        this.shift = shift;
    }

    @Override
    public void on(NoteEvent event) {
        int tone = event.getTone() + shift;
        if (event.isOn()) channels[0].noteOn(tone, 80);
        else channels[0].noteOff(tone);
    }

    void dispose() {
        synth.close();
    }
}
