package com.pheminist.model.MIDI;

import com.badlogic.gdx.files.FileHandle;

import java.io.Serializable;

public class HData implements Serializable {
    private HNote[] hNotes;
    private int ppqn; // Pulses Per Quarter Note
    private int nTones; // number of tones in the melody
    private int[] tones; // array of tones using in this melody. Value in 0..127
    //    private int[] toneIndexes=new int[128]; // for getIndexByTone()
    private int index = 0;
    private int tickDuration = 100;  // tick duration in microsecond
    private float temp;

    public int getPpqn() {
        return ppqn;
    }

    public float getTemp() {
        return temp;
    }

    public void setPpqn(int ppqn) {
        this.ppqn = ppqn;
    }

    public int getnTones() {
        return nTones;
    }

    public int[] getTones() {
        return tones;
    }

    public void sethNotes(HNote[] hNotes) {
        this.hNotes = hNotes;
    }

    public void setnTones(int nTones) {
        this.nTones = nTones;
    }

    public void setTones(int[] tones) {
        this.tones = tones;
    }

    public HNote[] getHNotes() {
        return hNotes;
    }

    public int getNexTime() {
        return hNotes[index].getTime();
    }

    public boolean hasNext() {
        return (index < hNotes.length);
    }

    public HNote getNext() {
        return hNotes[index++];
    }

    public int getTickDuration() {
        return tickDuration;
    }

    public void setTickDuration(int tickDuration) {
        this.tickDuration = tickDuration;
    }

    public void setTemp(int tempo) {
        temp=(float)tempo;
    }

    public void resetIndex(){index=0;}

    public float getTotalTicks(){
        HNote hn=hNotes[hNotes.length-1];
        return (hn.getTime()+hn.getDuration());
    }

    public void setIndexByTick(float tick){
        resetIndex();
        for (int i=0;i<hNotes.length;i++){
            if(hNotes[i].getTime()>tick) {
                index=i;
                return;
            }
        }
    }

    public static HData getInstance(FileHandle file){
        MIDIData mMIDIData = new MIDIData(file.file());
        HData hData=mMIDIData.midiDataToHData();
        hData.resetIndex();
        return hData;
    }


}
