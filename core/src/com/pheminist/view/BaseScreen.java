package com.pheminist.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pheminist.controller.Controller;
import com.pheminist.model.Model;

public abstract class BaseScreen implements Screen {
    private final float V_WIDTH =  1366f;
    protected Controller controller;
    protected Model model;
    protected Stage stage;

    public BaseScreen(Controller controller, Model model) {
        this.controller = controller;
        this.model=model;
        stage = new Stage(new FitViewport(V_WIDTH,getV_Height()));
    }
    private float getV_Height() {
        return V_WIDTH * (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
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
        stage.getViewport().setWorldSize(V_WIDTH,V_WIDTH*(float)height/(float) width);
        stage.getViewport().update(width, height,true);
    }

    @Override public void dispose() {
        if(stage != null) stage.dispose();
        stage = null;
    }
}
