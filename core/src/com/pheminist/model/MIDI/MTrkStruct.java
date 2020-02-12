package com.pheminist.model.MIDI;

import java.util.ArrayList;

public class MTrkStruct {
    private String name;
    private long length;
    private ArrayList<NoteStruct> arrayNoteStruct;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public ArrayList<NoteStruct> getArrayNoteStruct() {
        return arrayNoteStruct;
    }

    public void setArrayNoteStruct(ArrayList<NoteStruct> arrayNoteStruct) {
        this.arrayNoteStruct = arrayNoteStruct;
    }

    public int getNumOfEvents(){
        return arrayNoteStruct.size();
    }
}
