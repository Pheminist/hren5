package com.pheminist.model.MIDI;

public class MIDIHeader {
    public final String  nameSection; // Имя раздела. Должно быть 1297377380 т.е. "MThd".
    public final long lengthSection; // Длинна блока, 4 байта. Должно быть 0x6;
    public final int mode; // Режим MIDI файла: 0, 1 или 2.
    public final int numOfTracks; // Количество каналов.
    public final int ppq;  // Параметры тактирования.

    // Назначение: разбор главной структуры MIDI файла.
// Параметры: Открытый FileStream поток.
// Возвращаемой значение - заполненная структура типа MIDIheaderStruct.
    public MIDIHeader (byte[] fileArray)
    {
        MReader mReader=new MReader(fileArray);
//        MIDIheaderStruct ST = new MIDIheaderStruct(); // Создаем пустую структуру заголовка файла.
        nameSection      = mReader.readString(4); // Копируем имя раздела.
        lengthSection    = mReader.read32BigEndian(); // Считываем 4 байта длины блока. Должно в итоге быть 0x6
        mode             = mReader.read16BigEndian(); // Считываем 2 байта режима MIDI. Должно быть 0, 1 или 2.
        numOfTracks = mReader.read16BigEndian(); // Считываем 2 байта количество каналов в MIDI файле.
        ppq = mReader.read16BigEndian(); // Считываем 2 байта параметров тактирования.
        mReader=null;
    }

    public String getNameSection() {
        return nameSection;
    }

    public long getLengthSection() {
        return lengthSection;
    }

    public int getMode() {
        return mode;
    }

    public int getNumOfTracks() {
        return numOfTracks;
    }

    public int getPpq() {
        return ppq;
    }
}
