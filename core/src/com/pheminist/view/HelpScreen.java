package com.pheminist.view;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.pheminist.controller.Controller;
import com.pheminist.model.Model;

import static com.pheminist.model.Model.SKIN;

public class HelpScreen extends BaseScreen {
    private static final String text = " asdf sdf;lkfjas dflkj dfsfl;kj dfs sdf;lkj dsf ;lkjdf sdkflj "+
    "flsjh sadflkjhsadffasd lsdfkjhas df; s;dlfkfjas;ldfkja  asdf;lkasjdfas;ld f sadflkj fds ;jkjfda;lkj  ";
    public HelpScreen(final Controller controller, Model model) {
        super(controller, model);
        Label label = new Label(text,model.assetManager.get(SKIN, Skin.class));
        label.setWrap(true);
        label.setFillParent(true);
        stage.addActor(label);
        stage.addListener(new InputListener(){
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if(keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE){
                    controller.changeScreen(GScreen.class);
                    return true;
                }
                return false;
            }
        });
        setWorldWidth(800);
    }

    @Override
    public void init() {

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
