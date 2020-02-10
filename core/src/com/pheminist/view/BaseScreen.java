package com.pheminist.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pheminist.controller.Controller;
import com.pheminist.model.Model;

import static com.pheminist.model.Model.SKIN;

public abstract class BaseScreen implements Screen {
    protected Controller controller;
    protected Model model;
    protected Stage stage;

    public BaseScreen(Controller controller, Model model) {
        this.controller = controller;
        this.model=model;
        Skin skin = model.assetManager.get(SKIN, Skin.class);
        stage = new Stage(new ScreenViewport());
    }

    // === Lifecycle Methods === //
    @Override
    final public void show() {
        Gdx.input.setInputProcessor(stage);

        // Screen-specific initialization
        init();
    }

    public abstract void init();

    @Override
    final public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        draw(delta);
        if(stage != null) {
            stage.act(delta);
            stage.draw();
        }
    }

    /**
     * Override this sucker to implement any custom drawing
     * @param delta The number of seconds that have passed since the last frame.
     */
    public void draw(float delta) {}

    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override public void dispose() {
        if(stage != null) stage.dispose();
        stage = null;
    }
}
