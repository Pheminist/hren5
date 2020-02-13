package com.pheminist.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.pheminist.controller.Controller;
import com.pheminist.model.Model;

public class GScreen extends BaseScreen {
    private final float V_WIDTH = 1366f;

    private Table screenTable = new Table();

    private Hud hud;
    private NR nr;

    public GScreen(Controller controller, Model model) {
        super(controller, model);
    }

    @Override
    public void init() {
        stage.clear();

        screenTable.clear();
        hud = new Hud(controller, model);
        nr = new NR(model.nrModel);

        Table testTable = new Table();
        testTable.add(nr).expand().fill().row();
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
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
    }

}
