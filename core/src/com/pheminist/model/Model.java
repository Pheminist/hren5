package com.pheminist.model;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.pheminist.interfaces.IHorner;
import com.pheminist.model.MIDI.HFNoteHandler;

public class Model {
    public final static String SKIN="skin/gr1.json";

    public final SecondPerScreen sps = new SecondPerScreen();
    public final Tempo tempo = new Tempo();
    public final Shift shift = new Shift();
    public final NoteEvent noteEvent = new NoteEvent();
    public final WindowSize windowSize = new WindowSize();
    public final IHorner horner;

//    private HData hData;
    private HFNoteHandler hData;

    public NRModel nrModel = new NRModel(this);
    public Beeper beeper;

    public HFNoteHandler gethData() {
        return hData;
    }

    public void sethData(HFNoteHandler hData) {
        this.hData = hData;
        nrModel=new NRModel(this);
    }

    public final AssetManager assetManager;

    public Model(IHorner horner) {
        this.horner=horner;
        beeper = new Beeper(this);
        assetManager=new AssetManager();
        assetManager.load(SKIN, Skin.class);
        assetManager.finishLoading();
    }

    public void dispose(){
        assetManager.dispose();
        beeper.dispose();
    }
}
