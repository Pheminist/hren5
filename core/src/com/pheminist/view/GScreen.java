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
    private NButtonsView nButtonsView;
    private Hud hud;

    public GScreen(final Controller controller, Model model) {
        super(controller, model);
        Gdx.input.setCatchKey(Input.Keys.BACK,true);
        hud = new Hud(controller, model);
//        model.pause.getPublisher().addListener(hud.recBtn.getPauseListener());
        model.noteEvent.getPublisher().addListener(model.beeper);
    }

    @Override
    public void init() {
        hud.playView.update();
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
//        model.nrState.getPublisher().addListener(hud.recBtn);

        nr = new NR(model.nrModel);
        LineView lineView=new LineView(model);
        nButtonsView = new NButtonsView(model);
        nButtonsView.addListeners();

        Table testTable = new Table();
        testTable.add(nr).expand().fill().row();
        testTable.add(lineView).height(5f).expandX().fillX().row();
        testTable.add(nButtonsView).expandX().fillX();
        screenTable.add(testTable).expand().fill();
        screenTable.add(hud).width(365).expandY().fillY();
//        screenTable.setDebug(true);

        stage.addActor(screenTable);
        screenTable.setFillParent(true);
        hud.updateSoundState();
        controller.setTime(model.time.getTime());
    }

    @Override
    public void draw(float delta) {
        nr.update(Gdx.graphics.getRawDeltaTime());
    }

    @Override
    public void dispose() {
    }

    @Override
    public void pause() {
        controller.stopRecord();
        model.beeper.allNotesOff();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        controller.stopRecord();
        controller.removeCameraView();
        nButtonsView.removeListeners();
        model.noteEvent.getPublisher().removeAllListeners();
        controller.setSound(false);
        model.pause.setPause(true);
    }

}
