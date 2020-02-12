package com.pheminist.model;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.pheminist.model.MIDI.HData;

public class Model {
    public final static String SKIN="skin/gr1.json";

    public final QPS gps = new QPS();
    public final Tempo tempo = new Tempo();
    public final NoteEvent noteEvent = new NoteEvent();

    private HData hData;

    public HData gethData() {
        return hData;
    }

    public void sethData(HData hData) {
        this.hData = hData;
    }

    public final AssetManager assetManager;

    public Model() {
        assetManager=new AssetManager();
        assetManager.load(SKIN, Skin.class);
        assetManager.finishLoading();
    }

    public void dispose(){
        assetManager.dispose();
    }
}
