package com.pheminist.model;

import java.util.Arrays;

public class IsNoteAlives extends SimpleEvent<IsNoteAlives>{

    private boolean[] isNoteAlives;

    public IsNoteAlives(int size) {
        isNoteAlives=new boolean[size];
        Arrays.fill(isNoteAlives,true);
    }

    public boolean isAlive(int i) {
        return isNoteAlives[i];
    }

    public void setAlive(int i,boolean b) {
        isNoteAlives[i]=b;
        publisher.fire(this);
    }
}
