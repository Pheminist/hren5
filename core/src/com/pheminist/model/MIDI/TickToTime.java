package com.pheminist.model.MIDI;

import java.util.ArrayList;
import java.util.List;

import static com.pheminist.model.MIDI.MEventReader.TEMPO_STATUS;

class TickToTime {
    private final int size;
    private float[] tickDurations;
    private long[] ticks;

    TickToTime(MidiData midiData) {
        MEventReader mEventReader = new MEventReader(midiData.getTracks()[0]);

        List<TempoEvent> tempoEvents = new ArrayList<>();
        float ppqn = midiData.getMidiHeader().ppq;

        while (mEventReader.readNext()) {
            if (mEventReader.getStatus() == TEMPO_STATUS) {
                tempoEvents.add(new TempoEvent(mEventReader.getCurTick(), mEventReader.getTempo() / ppqn*0.000001f));
            }
        }

        size = tempoEvents.size();
        tickDurations = new float[size];
        ticks = new long[size];

        for (int i = 0; i < size; i++) {
            TempoEvent event = tempoEvents.get(i);
            ticks[i] = event.tick;
            tickDurations[i] = event.tickDuration;
        }
    }

    float tickToSecond(long tick) {
        float time = 0;
        int i;
        for (i = 0; i < size-1; i++) {
            if(ticks[i+1]>=tick) {
                break;
            }
            time = time + (ticks[i+1] - ticks[i]) * tickDurations[i];
        }
        return time + (tick-ticks[i]) * tickDurations[i];
    }

    private class TempoEvent {
        final int tick;
        final float tickDuration;

        TempoEvent(int tick, float tickDuration) {
            this.tick = tick;
            this.tickDuration = tickDuration;
        }
    }
}

