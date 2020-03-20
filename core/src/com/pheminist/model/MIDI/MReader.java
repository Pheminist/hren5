package com.pheminist.model.MIDI;

import java.util.Arrays;

public class MReader {
    private byte[] mBytes;
    private int index=0;

    public MReader(byte[] mBytes) {
        this.mBytes = mBytes;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
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

}
