package com.pheminist.model.MIDI;

import com.badlogic.gdx.files.FileHandle;

public class MidiFile {
    private MidiFile(){}

    public static byte[] getMidiArray(FileHandle fileHandle){

        return fileHandle.readBytes();
    }
}
