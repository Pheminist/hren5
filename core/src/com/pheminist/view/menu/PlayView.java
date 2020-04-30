package com.pheminist.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.pheminist.controller.Controller;
import com.pheminist.interfaces.IListener;
import com.pheminist.model.Model;
import com.pheminist.model.Time;

import java.util.Locale;

import static com.pheminist.model.Model.SKIN;
import static com.pheminist.view.Hud.VPAD;

public class PlayView extends Table {
    private final static float START_TIME=-2f;
    private final Model model;
    private  float totalTime;
    final Slider playSlider;
    final Label playTimeLabel;

    public PlayView(final Controller controller, final Model model) {
        this.model=model;
        final Skin skin = model.assetManager.get(SKIN, Skin.class);
        totalTime=model.gethData().getTotalTime();
        TextButton replayButton = new TextButton("R", skin);
        TextButton fullScreenBtn = new TextButton("[ ]", skin);
        final ImageButton playPlusBtn1 = new ImageButton(skin, "play");
//        playPlusBtn1.setChecked(true);
        playTimeLabel = new Label("", skin);
        playTimeLabel.setAlignment(Align.right);
        playTimeLabel.setFontScale(0.8f);
        playSlider = new Slider(START_TIME, totalTime, .1f, false, skin);
        pad(VPAD, 10, VPAD, 10);
        setSkin(skin);
        background("button-pressed");
        defaults().minHeight(40).prefHeight(40).pad(0, 2, 0, 2);
        add(replayButton).minWidth(40).prefWidth(40);
        add(fullScreenBtn).minWidth(40).prefWidth(40);
        add(playPlusBtn1).minWidth(40).prefWidth(40);
        add(playTimeLabel).expandX().align(Align.right).padRight(20);
        row();
        add(playSlider).colspan(4).expandX().fillX();

        replayButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setTime(START_TIME);
            }
        });

        fullScreenBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(Gdx.graphics.isFullscreen()){
                    Gdx.graphics.setWindowedMode(model.windowSize.getWidth(),model.windowSize.getHeight());
                }
                else {
                    model.windowSize.storeSize();
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                }
//                System.out.println(" width = "+ Gdx.graphics.getWidth()+" height = "+ Gdx.graphics.getHeight());
            }
        });

        playSlider.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setTime(playSlider.getValue());
                return true;
            }
        });

        model.time.getPublisher().addListener(new IListener<Time>() {
            @Override
            public void on(Time event) {
                float time = model.time.getTime();
                playTimeLabel.setText(String.format(Locale.UK, "%.0f  /  %.0f", time,totalTime));
                playSlider.setValue(time);
            }
        });
    }

    public void update(){
        totalTime=model.gethData().getTotalTime();
        playSlider.setRange(START_TIME,totalTime);
        playTimeLabel.setText(String.format(Locale.UK, "%.0f  /  %.0f", START_TIME,totalTime));
        playSlider.setValue(START_TIME);

    }

    private void setTime(float time){
        model.nrModel.allNotesOffByEvents();
        model.gethData().setIndexByTime(time);
        model.time.setTime(time);
    }
}
