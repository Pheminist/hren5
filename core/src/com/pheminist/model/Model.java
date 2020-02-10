package com.pheminist.model;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Model {
    public final static String SKIN="skin/gr1.json";

    public final QPS gps = new QPS();

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
