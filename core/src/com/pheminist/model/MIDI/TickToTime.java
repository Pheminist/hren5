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
        long curTick = 0;
        int i;
        for (i = 0; i < size && ticks[i] <= tick; i++) {
            time = time + (ticks[i] - curTick) * tickDurations[i];
            curTick = ticks[i];
        }
        return time + (tick - curTick) * tickDurations[i - 1];
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

//class TickToTime {
////    private static final int META_TEMPO_TYPE = 0x51;
//    private float[] tickDurations;
//    private long[] ticks;
//    private final int size;
//
//    TickToTime(MidiData midiData) {
//        Track track = midiData.getTracks()[0];
//
//        List<MidiEvent> tempoEvents = new ArrayList<>();
//        float ppqn = midiData.getMidiHeader().ppq;
//
//        for (int i = 0; i < track.size(); i++) {
//            MidiEvent event = track.get(i);
//            MidiMessage message = event.getMessage();
//            float tempo = getTempo(message);
//            if (tempo > -0.1) {
//                tempoEvents.add(event);
//            }
//        }
//
//        size = tempoEvents.size();
//        tickDurations = new float[size];
//        ticks = new long[size];
//
//        for (int i = 0; i < size; i++) {
//            MidiEvent event = tempoEvents.get(i);
//            MetaMessage message = (MetaMessage) event.getMessage();
//            ticks[i] = event.getTick();
//            float tempo = getTempo(message);
//            tickDurations[i] = tempo / ppqn;
//        }
//    }
//
//    private static float getTempo(MidiMessage midiMsg) {
//        // first check if it is a META message at all
//        if (midiMsg.getLength() != 6
//                || midiMsg.getStatus() != MetaMessage.META) {
//            return -1;
//        }
//        byte[] msg = midiMsg.getMessage();
//        if (((msg[1] & 0xFF) != META_TEMPO_TYPE) || (msg[2] != 3)) {
//            return -1;
//        }
//        int tempo = (msg[5] & 0xFF)
//                | ((msg[4] & 0xFF) << 8)
//                | ((msg[3] & 0xFF) << 16);
//        return (float)tempo / 1000000;
//    }
//
//    float tickToSecond(long tick){
//        float time = 0;
//        long curTick=0;
//        int i;
//        for (i=0;i<size && ticks[i]<=tick;i++){
//                time=time+(ticks[i]-curTick)*tickDurations[i];
//                curTick=ticks[i];
//        }
//        return time+(tick-curTick)*tickDurations[i-1];
//    }
//}
