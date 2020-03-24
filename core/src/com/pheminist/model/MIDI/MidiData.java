package com.pheminist.model.MIDI;

import com.badlogic.gdx.files.FileHandle;

import java.util.Arrays;

public class MidiData {
    private final static int HEADER_LENGTH = 14;
    private final Track[] tracks;
    private final MIDIHeader midiHeader;

    public MidiData(FileHandle fileHandle) {
        byte[] fileBytes = fileHandle.readBytes();
        midiHeader = new MIDIHeader(fileBytes);
        MReader mReader = new MReader(fileBytes);
        tracks = new Track[midiHeader.getNumOfTracks()];
        mReader.setIndex(HEADER_LENGTH);

        for (int i = 0; i < midiHeader.getNumOfTracks(); i++) {  // fill the array of tracks
            mReader.readString(4); // Копируем имя раздела.
            int length = (int) mReader.read32BigEndian(); // 4 байта длинны всего блока.
            int startIndex = mReader.getIndex();
            int endIndex = startIndex + length;
            Track track = new Track(Arrays.copyOfRange(fileBytes, startIndex, endIndex));
            mReader.setIndex(endIndex);
            tracks[i] = track;
        }
    }

    public Track[] getTracks() {
        return tracks;
    }

    public MIDIHeader getMidiHeader() {
        return midiHeader;
    }
}
