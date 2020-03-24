package com.pheminist.model.MIDI;

public class MEventReader {
    public final static int NOTE_ON = 0x90;
    public final static int NOTE_OFF = 0x80;
    public final static int META_TEMPO_TYPE = 0x51;
    public final static int TEMPO_STATUS = 0x51;

    public final static int META_EVENT = 0;
    public final static int SYSEX_EVENT = 1;
    public final static int MIDI_EVENT = 2;


    private Track track;

    private MReader mReader;
    private int curTick;

    private int status;
    private boolean lastNoteOn;
    private int channel;
    private int tone;
    private int tempo;

    public MEventReader(Track track) {
        this.track = track;
        mReader = new MReader(track.bytes);

    }

    public boolean readNext() {
        if (mReader.getIndex() >= track.bytes.length) return false;

        curTick += mReader.readVarLengthInt(); // Получаем реальное время.

        int buffer = mReader.readByte();  // Сюда кладем считанное значение.
        // Если у нас не статус-байт
        if ((buffer & 0x80) == 0) {
            tone = buffer;
            int value = mReader.readByte();
            if (lastNoteOn) {
                if (value == 0) status = NOTE_OFF;
                else status = NOTE_ON;
            } else status = NOTE_OFF;
        }

        // Если не мета-событие, то смотрим, является ли событие событием первого уровня.
        else switch (buffer & 0xF0) // Смотрим по старшым 4-м байтам.
        {
            // Перебираем события первого уровня.

            case 0x80: // Снять клавишу.
                channel = buffer & 0x0F;
                lastNoteOn = false;
                tone = mReader.readByte();
                mReader.readByte(); // value
                status=NOTE_OFF;
                break;
            case 0x90:   // Нажать клавишу.
                channel = buffer & 0x0F;
                lastNoteOn = true;
                tone = mReader.readByte();
                if (mReader.readByte() == 0) status = NOTE_OFF; // value
                else status = NOTE_ON;
                break;
            case 0xA0:  // Сменить силу нажатия клавишы.
                mReader.skip(2);
                break;
            // Если 2-х байтовая комманда.
            case 0xB0:
                int buffer2level = mReader.readByte(); // Читаем саму команду.
                switch (buffer2level) // Смотрим команды второго уровня.
                {
                    default: // Для определения новых комманд (не описаных).
                        mReader.readByte(); // Считываем параметр какой-то неизвестной функции.
                        break;
                }
                break;

            // В случае попадания их просто нужно считать.
            case 0xC0:   // Просто считываем байт номера.
                mReader.readByte(); // Считываем номер программы.
                break;

            case 0xD0:   // Сила канала.
                mReader.readByte(); // Считываем номер программы.
                break;

            case 0xE0:  // Вращения звуковысотного колеса.
                mReader.read16BigEndian(); // Считываем номер программы.
                break;

            case 0xF0:
                switch (buffer) {
                    case 0xFF: // Meta event
                        int metaType = mReader.readByte();
                        switch (metaType) {
                            case META_TEMPO_TYPE:
                                mReader.skip(1);
                                tempo = mReader.read24BigEndian();
                                status = TEMPO_STATUS;
                                System.out.println("TEMPO -------------------");
                                break;
                            default:
                                mReader.skip(mReader.readVarLengthInt());
                                break;
                        }
                        break;
                    case 0xF0: // SysEx event
                    case 0xF7: // Escape event
                        mReader.skip(mReader.readVarLengthInt());
                        break;

                }
                break;
        }

        return true;
    }

    public int getCurTick() {
        return curTick;
    }

    public int getStatus() {
        return status;
    }

    public int getChannel() {
        return channel;
    }

    public int getTone() {
        return tone;
    }

    public int getTempo() {
        return tempo;
    }
}