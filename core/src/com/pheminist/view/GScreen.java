package com.pheminist.view;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.pheminist.controller.Controller;
import com.pheminist.model.Model;

public class GScreen extends BaseScreen {

    private Table screenTable = new Table();

    private NR nr;

    public GScreen(Controller controller, Model model) {
        super(controller, model);
    }

    @Override
    public void init() {
        stage.clear();

        screenTable.clear();
        Hud hud = new Hud(controller, model);
        nr = new NR(model.nrModel);
        NButtonsView nButtonsView = new NButtonsView(model);

        Table testTable = new Table();
        testTable.add(nr).expand().fill().row();
        testTable.add(nButtonsView).expandX().fillX();
        screenTable.add(testTable).expand().fill();
        screenTable.add(hud).width(365).expandY().fillY();
        screenTable.setDebug(true);

        stage.addActor(screenTable);
        screenTable.setFillParent(true);
    }

    @Override
    public void draw(float delta) {
        nr.update(delta);
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
        model.noteEvent.getPublisher().removeAllListeners();
        model.sps.getPublisher().removeAllListeners();
        model.shift.getPublisher().removeAllListeners();
        model.tempo.getPublisher().removeAllListeners();
        model.nrModel.time.getPublisher().removeAllListeners();        // ?
    }

}
