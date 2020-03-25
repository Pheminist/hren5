package com.pheminist;

import com.pheminist.interfaces.IHorner;

import org.billthefarmer.mididriver.MidiDriver;

public class AndroidHorner implements IHorner {
    protected MidiDriver midi;
    private byte msg2[] = new byte[2];
    private byte msg3[] = new byte[3];
    private final byte VALUE = 80;

    public AndroidHorner() {
        midi = new MidiDriver();
        midi.start();
        msg2[0] = (byte) 0xc0;
        msg2[1] = (byte) 21;
        midi.write(msg2);
    }

    @Override
    public void init() {

    }

    @Override
    public void noteOn(int channel, int tone) {
        msg3[0] = (byte) 0x90;
        msg3[1] = (byte) tone;
        msg3[2] = (byte) VALUE;

        midi.write(msg3);
    }

    @Override
    public void noteOff(int channel, int tone) {
        msg3[0] = (byte) 0x80;
        msg3[1] = (byte) tone;
        msg3[2] = (byte) VALUE;

        midi.write(msg3);
    }

    @Override
    public void allNotesOff() {
        msg3[0] = (byte) 0xB0;
        msg3[1] = (byte) 0x7b;
        msg3[2] = (byte) 0;

        midi.write(msg3);
    }

    @Override
    public void dispose() {
        midi.stop();
    }
}
