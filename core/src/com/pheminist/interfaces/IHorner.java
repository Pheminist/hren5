package com.pheminist.interfaces;

import com.pheminist.model.Model;

public interface IHorner {
    void init();
    void noteOn(int channel,int tone);
    void noteOff(int channel,int tone);
    void allNotesOff();
    void dispose();
}
