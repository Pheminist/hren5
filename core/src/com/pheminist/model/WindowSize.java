package com.pheminist.model;

import com.badlogic.gdx.Gdx;

public class WindowSize {
    private int width=100;
    private int height=100;

    public void storeSize(){
        width= Gdx.graphics.getWidth();
        height=Gdx.graphics.getHeight();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
