package com.pheminist.model.MIDI;

public class MIDIHeader {
    public String  nameSection; // Имя раздела. Должно быть 1297377380 т.е. "MThd".
    public long lengthSection; // Длинна блока, 4 байта. Должно быть 0x6;
    public int mode; // Режим MIDI файла: 0, 1 или 2.
    public int numOfTracks; // Количество каналов.
    public int settingTime;  // Параметры тактирования.

    // Назначение: разбор главной структуры MIDI файла.
// Параметры: Открытый FileStream поток.
// Возвращаемой значение - заполненная структура типа MIDIheaderStruct.
    public MIDIHeader readHeader(MIDIData midiData)
    {
        midiData.setIndex(0);
//        MIDIheaderStruct ST = new MIDIheaderStruct(); // Создаем пустую структуру заголовка файла.
        nameSection      = midiData.readString(4); // Копируем имя раздела.
        lengthSection    = midiData.read32BigEndian(); // Считываем 4 байта длины блока. Должно в итоге быть 0x6
        mode             = midiData.read16BigEndian(); // Считываем 2 байта режима MIDI. Должно быть 0, 1 или 2.
        numOfTracks = midiData.read16BigEndian(); // Считываем 2 байта количество каналов в MIDI файле.
        settingTime      = midiData.read16BigEndian(); // Считываем 2 байта параметров тактирования.
        return this; // Возвращаем заполненную структуру.
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

    public int getSettingTime() {
        return settingTime;
    }
}
