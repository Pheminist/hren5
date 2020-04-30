package com.pheminist.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.pheminist.interfaces.IHorner;
import com.pheminist.model.MIDI.HFNoteHandler;
import com.pheminist.model.MIDI.HNoteProvider;
import com.pheminist.model.MIDI.MidiData;

public class Model {
    public final static String SKIN="skin/gr1.json";

    public final SecondPerScreen sps = new SecondPerScreen();
    public final Time time = new Time();
    public final Tempo tempo = new Tempo();
    public final Shift shift = new Shift();
    public final NoteEvent noteEvent = new NoteEvent();
    public final NRState nrState = new NRState(NRState.PAUSED);
    public final WindowSize windowSize = new WindowSize();
    public final IHorner horner;
    public final InputFile inputFile = new InputFile(Gdx.files.internal("melodies/OldMaple.mid"));
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
        sethData(new HFNoteHandler(new HNoteProvider(new MidiData(inputFile.getFile()))));
    }

    public void dispose(){
        assetManager.dispose();
        beeper.dispose();
    }
}
