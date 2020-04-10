package com.pheminist.model.MIDI;

class ActiveNote {
    int number;
    long time;

    ActiveNote(int number, long time) {
        this.number = number;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActiveNote that = (ActiveNote) o;
        return number == that.number &&
                time == that.time;
    }
}
