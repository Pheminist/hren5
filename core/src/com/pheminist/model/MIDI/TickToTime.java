package com.pheminist.model.MIDI;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

class TickToTime {
    private static final int META_TEMPO_TYPE = 0x51;
    private float[] tickDurations;
    private long[] ticks;
    private final int size;

    TickToTime(Sequence sequence) {
        Track track = sequence.getTracks()[0];
        List<MidiEvent> tempoEvents = new ArrayList<>();
        float ppqn = sequence.getResolution();

        for (int i = 0; i < track.size(); i++) {
            MidiEvent event = track.get(i);
            MidiMessage message = event.getMessage();
            float tempo = getTempo(message);
            if (tempo > -0.1) {
                tempoEvents.add(event);
            }
        }

        size = tempoEvents.size();
        tickDurations = new float[size];
        ticks = new long[size];

        for (int i = 0; i < size; i++) {
            MidiEvent event = tempoEvents.get(i);
            MetaMessage message = (MetaMessage) event.getMessage();
            ticks[i] = event.getTick();
            float tempo = getTempo(message);
            tickDurations[i] = tempo / ppqn;
        }
    }

    private static float getTempo(MidiMessage midiMsg) {
        // first check if it is a META message at all
        if (midiMsg.getLength() != 6
                || midiMsg.getStatus() != MetaMessage.META) {
            return -1;
        }
        byte[] msg = midiMsg.getMessage();
        if (((msg[1] & 0xFF) != META_TEMPO_TYPE) || (msg[2] != 3)) {
            return -1;
        }
        int tempo = (msg[5] & 0xFF)
                | ((msg[4] & 0xFF) << 8)
                | ((msg[3] & 0xFF) << 16);
        return (float)tempo / 1000000;
    }

    float tickToSecond(long tick){
        float time = 0;
        long curTick=0;
        int i;
        for (i=0;i<size && ticks[i]<=tick;i++){
                time=time+(ticks[i]-curTick)*tickDurations[i];
                curTick=ticks[i];
        }
        return time+(tick-curTick)*tickDurations[i-1];
    }
}
