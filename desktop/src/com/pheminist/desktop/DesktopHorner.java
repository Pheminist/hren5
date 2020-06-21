package com.pheminist.desktop;

import com.pheminist.interfaces.IHorner;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

public class DesktopHorner implements IHorner {
    private Synthesizer synth;
    private MidiChannel[] channels;

    public DesktopHorner() {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channels = synth.getChannels();
//            channels[0].programChange(22);
            channels[0].programChange(76);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void noteOn(int channel, int tone) {
        channels[0].noteOn(tone, 80);
    }

    @Override
    public void noteOff(int channel, int tone) {
        channels[0].noteOff(tone);
    }

    @Override
    public void allNotesOff() {
        channels[0].allNotesOff();
    }

    @Override
    public void dispose() {
        synth.close();
    }
}
