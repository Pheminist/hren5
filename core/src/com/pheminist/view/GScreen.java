package com.pheminist.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pheminist.controller.Controller;
import com.pheminist.model.Model;

import java.util.ArrayList;
import java.util.List;

import static com.pheminist.model.Model.SKIN;

public class GScreen extends BaseScreen {
    private final float V_WIDTH =  1366f;

    private Table screenTable=new Table();

    private Hud hud;

    public GScreen(Controller controller, Model model) {
        super(controller,model);
        Skin skin = model.assetManager.get(SKIN, Skin.class);
//        stage = new Stage(new FitViewport(V_WIDTH, getV_Hight()));

    }

    private List<NoteListener> noteListeners = new ArrayList<>();

    public interface NoteListener {
        void noteEvent(int note, int tone, boolean isOn);
    }

    public void addNoteListener(GScreen.NoteListener listener) {
        if (!noteListeners.contains(listener)) noteListeners.add(listener);
    }

    public void removeNoteListener(GScreen.NoteListener listener) {
        noteListeners.remove(listener);
    }

    public void removeAllNoteListener() {
        noteListeners.clear();
    }

    protected void notifyNoteListeners(final int note, final int tone, final boolean isOn) {
        for (GScreen.NoteListener listener : noteListeners) {
            listener.noteEvent(note, tone, isOn);
        }
    }

    float getV_Hight() {
        float aspRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        return V_WIDTH * aspRatio;
    }

    @Override
    public void init() {
        stage.clear();

        screenTable.clear();
//        this.clear();

//        paused = false;

//        hud = new Hud(parent, this);
        hud = new Hud(controller,model);

        Table testTable=new Table();
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



//        stage.addActor(this);
//        this.setFillParent(true);
        stage.addActor(screenTable);
        screenTable.setFillParent(true);


//        if (parent.getPreferences().isMusicEnabled()) addNoteListener(beeper);
////        if(Gdx.app.getType()== Application.ApplicationType.Desktop) addNoteListener(beeper);
//        addNoteListener(notesRenderer);

    }

    @Override
    public void dispose(){ }

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
        removeAllNoteListener();
    }

}
