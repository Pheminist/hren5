package com.pheminist.model.MIDI;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MIDIData {
    private byte[] mBytes;
    private int index =0;
//    private int numOfBytesOfVarLength=0;
    private MIDIHeader midiHeader;
    private int[] tones;
    private int numOfTracks;
    private int numOfTones;
    private int[] toneIndexes=new int[128]; // for getIndexByTone()
    private int ppqn; // Pulses Per Quarter Note
    private int tempo;
    private HNote[] hNotes;

//    public static int getTempo() {
//        return tempo;
//    }

    public MIDIData(File file){
        FileInputStream fin=null;
        try
        {
            fin=new FileInputStream(file);
            System.out.printf("File size: %d bytes \n", fin.available());

            mBytes = new byte[fin.available()];
            fin.read(mBytes, 0, fin.available());
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public HNote[] gethNotes() {
        return hNotes;
    }

    byte[] getArray() {
        return mBytes;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String readString(int length){
        return new String(Arrays.copyOfRange(mBytes,index,index=index+length));
    }

    public void skip(int i){index+=i;}

    public byte readByte(){
        return mBytes[index++];
    }

    public int read16BigEndian(){
        long buffer = 0;
        for(int i=0;i<2;i++){
            buffer = buffer << 8;
            buffer |= byteToULong(mBytes[index+i]);
        }
        index=index+2;
        return (int) buffer;
    }

    public int read24BigEndian(){
        long buffer = 0;
        for(int i=0;i<3;i++){
            buffer = buffer << 8;
            buffer |= byteToULong(mBytes[index+i]);
        }
        index=index+3;
        return (int) buffer;
    }

    public long read32BigEndian(){
        long buffer = 0;
        for(int i=0;i<4;i++){
            buffer = buffer << 8;
            buffer |= byteToULong(mBytes[index+i]);
        }
        index=index+4;
        return buffer;
    }

    public int readVarLengthInt() { //todo exception for more then 4 bytes
//        numOfBytesOfVarLength=0;
        byte buffer; // Сюда кладем считанное значение.
        int result = 0; // Считанное время помещаем сюда.
        do {
//            numOfBytesOfVarLength++;
            buffer = readByte(); // Читаем значение.
            result <<=  7; // Сдвигаем на 7 байт влево существующее значенеи времени (Т.к. 1 старший байт не используется).
            result |= (byte)(buffer & (0x7F)); // На сдвинутый участок накладываем существующее время.
        } while ((buffer & (1<<7)) != 0); // Выходим, как только прочитаем последний байт времени (старший бит = 0).
        return result;
    }

    private long byteToULong(byte b){
        return (long) b&0xFF;
    }

// Назначение: копирование блока MTrk (блок с событиями) из MIDI файла.
// Параметры: поток для чтения MIDI файла.
// Возвращает: структуру блока с массивом структур событий.
    public MTrkStruct readMTrk(){
        MTrkStruct st = new MTrkStruct(); // Создаем пустую структуру блока MIDI файла.
        st.setArrayNoteStruct(new ArrayList()); // Создаем в структуре блока динамический массив структур событий клавиш.
        st.setName(readString(4)); // Копируем имя раздела.
        System.out.println("tttt "+st.getName());
        st.setLength(read32BigEndian()); // 4 байта длинны всего блока.
        long endIndex=index+st.getLength();
        byte buf=0;
        boolean lastSetOn = false;
        int channel=0;
        int realTime = 0; // Реальное время внутри блока.
        while (index<endIndex) // Пока не считаем все события.
        {
            // Время описывается плавающим числом байт. Конечный байт не имеет 8-го разрядка справа (самого старшего).
            byte buffer; // Сюда кладем считанное значение.
            NoteStruct bufferNoteStruct;
            realTime += readVarLengthInt(); // Получаем реальное время.

            buffer = readByte();
            // Если у нас не статус-байт
            if((buffer & (byte)0x80) == (byte)0){
                bufferNoteStruct = new NoteStruct(); // Создаем запись о новой ноте (буферная структура, будем класть ее в arrayNoteStruct).
                bufferNoteStruct.setChannel(channel); // Копируем номер канала.
                bufferNoteStruct.setOn(lastSetOn); // последнее событие нажатия или отпускания клавиши.
                bufferNoteStruct.setNote(buffer); // Копируем номер ноты.
                bufferNoteStruct.setVolume(readByte()); // Копируем динамику ноты.
                bufferNoteStruct.setTime(realTime); // Присваеваем реальное время ноты.
                st.getArrayNoteStruct().add(bufferNoteStruct); // Сохраняем новую структуру.

            }



            // Если у нас мета-события, то...
            else if (buffer == (byte)0xFF)    // todo variable length value
            {
                if(readByte()==(byte)0x51){
                    skip(1);
                    tempo=read24BigEndian();
                    System.out.println("tempo  ------------- "+tempo);
                }
                else {

                // Считываем номер мета-события.
                buffer = readByte(); // Считываем длину.
                skip(buffer);}
            }

            // Если не мета-событие, то смотрим, является ли событие событием первого уровня.
            else switch (buffer & 0xF0) // Смотрим по старшым 4-м байтам.
            {
                // Перебираем события первого уровня.

                case 0x80 : // Снять клавишу.
                    bufferNoteStruct = new NoteStruct(); // Создаем запись о новой ноте (буферная структура, будем класть ее в arrayNoteStruct).
                    bufferNoteStruct.setChannel(channel=(buffer & 0x0F)); // Копируем номер канала.
                    bufferNoteStruct.setOn(lastSetOn=false); // Мы отпускаем клавишу.
                    bufferNoteStruct.setNote(readByte()); // Копируем номер ноты.
                    bufferNoteStruct.setVolume(readByte()); // Копируем динамику ноты.
                    bufferNoteStruct.setTime(realTime); // Присваеваем реальное время ноты.
                    st.getArrayNoteStruct().add(bufferNoteStruct); // Сохраняем новую структуру.
                    break;
                case 0x90:   // Нажать клавишу.
                    bufferNoteStruct = new NoteStruct(); // Создаем запись о новой ноте (буферная структура, будем класть ее в arrayNoteStruct).
                    bufferNoteStruct.setChannel(channel=(buffer & 0x0F)); // Копируем номер канала.
                    bufferNoteStruct.setNote(buf=readByte()); // Копируем номер ноты.
                    lastSetOn=true;
                    bufferNoteStruct.setOn(buf==0 ? false: true); // Мы нажимаем клавишу.
                    bufferNoteStruct.setVolume(readByte()); // Копируем динамику ноты.
                    bufferNoteStruct.setTime(realTime); // Присваеваем реальное время ноты.
                    st.getArrayNoteStruct().add(bufferNoteStruct); // Сохраняем новую структуру.
                   break;
                case 0xA0:  // Сменить силу нажатия клавишы.
                    bufferNoteStruct = new NoteStruct(); // Создаем запись о новой ноте (буферная структура, будем класть ее в arrayNoteStruct).
                    bufferNoteStruct.setChannel(channel=(buffer & 0x0F)); // Копируем номер канала.
                    bufferNoteStruct.setOn(lastSetOn=true); // Мы нажимаем клавишу.
                    bufferNoteStruct.setNote(readByte()); // Копируем номер ноты.
                    bufferNoteStruct.setVolume(readByte()); // Копируем НОВУЮ динамику ноты.
                    bufferNoteStruct.setTime(realTime); // Присваеваем реальное время ноты.
                    st.getArrayNoteStruct().add(bufferNoteStruct); // Сохраняем новую структуру.
                    break;
                // Если 2-х байтовая комманда.
                case 0xB0:  byte buffer2level = readByte(); // Читаем саму команду.
                    switch (buffer2level) // Смотрим команды второго уровня.
                    {
                        default: // Для определения новых комманд (не описаных).
                            readByte(); // Считываем параметр какой-то неизвестной функции.
                            break;
                    }
                    break;

                // В случае попадания их просто нужно считать.
                case 0xC0:   // Просто считываем байт номера.
                    readByte(); // Считываем номер программы.
                    break;

                case 0xD0:   // Сила канала.
                    readByte(); // Считываем номер программы.
                    break;

                case 0xE0:  // Вращения звуковысотного колеса.
                    read16BigEndian(); // Считываем номер программы.
                    break;
            }
        }
        return st; // Возвращаем заполненную структуру.
    }

    private MTrkStruct[] toMTrkArray(){
        MTrkStruct track;

        MTrkStruct[] mTrkStructs=new MTrkStruct[numOfTracks];
        System.out.println("Number of tracks : "+midiHeader.getNumOfTracks());

        for(int i= 0;i<midiHeader.getNumOfTracks();i++){  // fill the array of tracks
            track=readMTrk();
            mTrkStructs[i]=track;
            System.out.println("number of events in the track : "+track.getNumOfEvents());
        }
        return mTrkStructs;
    }

    private HNote[] toHNoteArray(MTrkStruct[] mTrkStructs) {
        ArrayList<NoteStruct> noteStructArrayList = new ArrayList<>();

        // сливаем все треки в один список noteStructArrayList
        for (MTrkStruct a : mTrkStructs) noteStructArrayList.addAll(a.getArrayNoteStruct());
//
        // в каждой ячейке массива tones[] будет содержаться список со всеми событиями, связанными с
        // одной из 128 нот (индекс соответсвует тону ноты)
        ArrayList<HNote>[] allTones = new ArrayList[128];
        //зополняем массив пустыми списками
//        for (ArrayList<HNote> b : tones) b = new ArrayList<>();
        for (int i=0;i<128;i++) allTones[i]=new ArrayList<>();

        //заполняем списки массива нотами формата HNote
        for (NoteStruct note:noteStructArrayList) {
            // если событие - нажатие ноты, то добавляем ноту в список соответструющего ей тона
            if(note.isOn())
                allTones[note.getNote()].add(new HNote(note.getNote(),note.getTime(),0));
            // если событие - отпускание, то вычисляем длительность и записываем в последнюю ноту
            else{
                ArrayList<HNote> hNotes = allTones[note.getNote()];
                if(!hNotes.isEmpty()) {
                    HNote hNote = hNotes.get(hNotes.size()-1);
                    hNote.setDuration(note.getTime() - hNote.getTime());
                }
            }
        }

        //считаем количество разных тонов в музыкальном произведении
        numOfTones=0;
        for (ArrayList<HNote> tone : allTones) if(!tone.isEmpty()) numOfTones++;

        //tones - массив тонов встречающихся в мелодии
        tones = new int[numOfTones];
        numOfTones=0;
        for (int i=0;i<allTones.length;i++) if(!allTones[i].isEmpty()){
            tones[numOfTones]=i;
            numOfTones++;
        }

        // сливаем списки в один
        ArrayList<HNote> hNotes = new ArrayList<>();
        for (int i = 0; i < 128; i++)  hNotes.addAll(allTones[i]);

        // сортируем получившийся список
        Collections.sort(hNotes);

        for (int i=0;i<numOfTones;i++)     // for getIndexByTone()
            toneIndexes[tones[i]]=i;

        for (HNote hn: hNotes) hn.setNote(toneIndexes[hn.getNote()]);

        for (HNote hn: hNotes)
            System.out.printf("time %20d  tone  %d   duration %d\n",hn.getTime(),hn.getNote(),hn.getDuration());

        return hNotes.toArray(new HNote[0]);
    }

    public int getTickDurationMksec(){return 60000000/tempo/ppqn;}

    public HData midiDataToHData(){
        HData hData=new HData();

        midiHeader=new MIDIHeader(); //read the header
        midiHeader.readHeader(this);
        ppqn=midiHeader.getSettingTime();
        System.out.println("+++++++ ppqn = "+ppqn);

        numOfTracks = midiHeader.getNumOfTracks();
        hNotes=toHNoteArray(toMTrkArray());
        hData.sethNotes(hNotes);
        hData.setTones(tones);
        hData.setnTones(numOfTones);
        hData.setPpqn(ppqn);
        hData.setTemp(tempo);

        return hData;
    }
}
