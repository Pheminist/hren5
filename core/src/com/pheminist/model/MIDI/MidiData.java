package com.pheminist.model.MIDI;

import com.badlogic.gdx.files.FileHandle;

import java.util.Arrays;

public class MidiData {
    final static int HEADER_LENGTH = 14;
    private Track[] tracks;
    private MIDIHeader midiHeader;

    public MidiData(FileHandle fileHandle) {
        byte[] fileBytes = fileHandle.readBytes();
        midiHeader = new MIDIHeader(fileBytes);
    }

    public Track[] toTracks(byte[] bytes) {
        MReader mReader = new MReader(bytes);
        Track[] tracks = new Track[midiHeader.getNumOfTracks()];
        mReader.setIndex(HEADER_LENGTH);

        for (int i = 0; i < midiHeader.getNumOfTracks(); i++) {  // fill the array of tracks
            mReader.readString(4); // Копируем имя раздела.
            int length = (int) mReader.read32BigEndian(); // 4 байта длинны всего блока.
            int startIndex = mReader.getIndex();
            int endIndex = startIndex + length;
            Track track = new Track(Arrays.copyOfRange(bytes, startIndex, endIndex));
            mReader.setIndex(endIndex);
            tracks[i] = track;
        }
        return tracks;

    }

}
