package com.pheminist.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.pheminist.controller.Controller;
import com.pheminist.model.Model;

public class GScreen extends BaseScreen {

    private Table screenTable = new Table();

    private NR nr;

    public GScreen(final Controller controller, Model model) {
        super(controller, model);
        Gdx.input.setCatchKey(Input.Keys.BACK,true);
    }

    @Override
    public void init() {
        stage.clear();
        stage.addListener(new InputListener(){
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if(keycode == Input.Keys.BACK){
                    Gdx.app.exit();
                    return true;
                }
                return false;
            }
        });
        controller.addCameraView();

        screenTable.clear();
        Hud hud = new Hud(controller, model);
        nr = new NR(model.nrModel);
        LineView lineView=new LineView(model);
        NButtonsView nButtonsView = new NButtonsView(model);

        Table testTable = new Table();
        testTable.add(nr).expand().fill().row();
        testTable.add(lineView).height(5f).expandX().fillX().row();
        testTable.add(nButtonsView).expandX().fillX();
        screenTable.add(testTable).expand().fill();
        screenTable.add(hud).width(365).expandY().fillY();
//        screenTable.setDebug(true);

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
