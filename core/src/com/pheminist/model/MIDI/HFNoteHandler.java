package com.pheminist.model.MIDI;

public class HFNoteHandler {
    private HNoteProvider provider;
    private int index;
    private HFNote[] notes;
    private int nSoundes; // number of sounds used in the melody

    public HFNoteHandler(final HNoteProvider provider) {
        this.provider = provider;
        notes=provider.getHNotes();
        nSoundes=provider.numberOfSounds();

    }

    public boolean hasNext(){
        return (index < notes.length);
    }

    public HFNote getNext(){
        return notes[index++];
    }

//    public float getNextTime(){
//        return notes[index].getTime();
//    }

    public int getTone(int sound){
        return provider.getNotes()[sound].tone;
    }

    public int getChannel(int sound){
        return provider.getNotes()[sound].channel;
    }

    public int getnSoundes() {
        return nSoundes;
    }

    public float getTotalTime(){
        if(notes.length==0) return 0;
        HFNote note=notes[notes.length-1];
        return (note.getTime()+note.getDuration());
    }


    public void setIndexByTime(float time){
        index=0;
        for (int i=0;i<notes.length;i++){
            if(notes[i].getTime()>time) {
                index=i;
                return;
            }
        }
    }

    public float getNexTime() {
        return notes[index].getTime();
    }
}
