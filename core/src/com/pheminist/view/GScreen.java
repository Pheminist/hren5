package com.pheminist.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pheminist.controller.Controller;
import com.pheminist.model.Model;

public class GScreen extends BaseScreen {
    private final float V_WIDTH = 1366f;

    private Table screenTable = new Table();

    private Hud hud;

    public GScreen(Controller controller, Model model) {
        super(controller, model);
    }

    float getV_Hight() {
        float aspRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        return V_WIDTH * aspRatio;
    }

    @Override
    public void init() {
        stage.clear();

        screenTable.clear();
        hud = new Hud(controller, model);

        Table testTable = new Table();
//        testTable.add(notesRenderer).expand().fill().row();
//        nButtonsRenderer=new NButtonsRenderer(parent,parent.hData);
//        addNoteListener(nButtonsRenderer);
//
//        testTable.add(nButtonsRenderer).expandX().fillX();
//        testTable.setDebug(tru[e);
////        testTable.add(new TextButton("test  button 1",skin)).height(100).expandX().fill();

//        this.add(testTable).expand().fill();
//        this.add(hud).width(365).expandY().fillY();
//        this.setDebug(true);
        screenTable.add(testTable).expand().fill();
        screenTable.add(hud).width(365).expandY().fillY();
        screenTable.setDebug(true);

        stage.addActor(screenTable);
        screenTable.setFillParent(true);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void resize(int width, int height) {
        Viewport viewport = stage.getViewport();
        viewport.setWorldSize(V_WIDTH, getV_Hight());
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
    }

}
