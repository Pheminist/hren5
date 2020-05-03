package com.pheminist.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.pheminist.controller.Controller;
import com.pheminist.model.MIDI.HFNoteHandler;
import com.pheminist.model.Model;

public class NoteButtonsScreen extends BaseScreen {

    private Table bTable;

    public NoteButtonsScreen(final Controller controller, Model model) {
        super(controller, model);
        stage.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE) {
                    controller.changeScreen(GScreen.class);
                    return true;
                }
                return false;
            }
        });

        bTable = new Table();
    }

    @Override
    public void init() {
        HFNoteHandler hData = model.gethData();
        bTable.clear();

        for (int i = 0; i < hData.getnSoundes(); i++) {
            if (i % 6 == 0) bTable.row();
            NoteButton noteButton = new NoteButton(model, i);
            bTable.add(noteButton).expandX().fillX().uniform().minWidth(10);
            stage.addActor(bTable);
        }
        bTable.top();
        bTable.setFillParent(true);


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
