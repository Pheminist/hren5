package com.pheminist.view.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.pheminist.controller.Controller;
import com.pheminist.interfaces.IListener;
import com.pheminist.model.Model;
import com.pheminist.model.Time;

import java.util.Locale;

import static com.pheminist.model.Model.SKIN;
import static com.pheminist.view.Hud.VPAD;

public class PlayView extends Table {
    private  float totalTime;
    public PlayView(final Controller controller, final Model model) {
        final Skin skin = model.assetManager.get(SKIN, Skin.class);
        totalTime=model.gethData().getTotalTime();
        TextButton playMinusBtn = new TextButton("-", skin);
        TextButton playPlusBtn = new TextButton("+", skin);
        final ImageButton playPlusBtn1 = new ImageButton(skin, "play");
        playPlusBtn1.setChecked(true);
        final Label playTimeLabel = new Label("44:44/44:44", skin);
        playTimeLabel.setAlignment(Align.right);
        playTimeLabel.setFontScale(0.8f);
        final Slider playSlider = new Slider(-2f, model.gethData().getTotalTime(), .1f, false, skin);
//        final Slider playSlider = new Slider(0f, 200f, 0.01f, false, skin);
        pad(VPAD, 10, VPAD, 10);
//        tempoTable.setDebug(true);
        setSkin(skin);
        background("button-pressed");
        defaults().minHeight(40).prefHeight(40).pad(0, 2, 0, 2);
        add(playMinusBtn).minWidth(40).prefWidth(40);
        add(playPlusBtn).minWidth(40).prefWidth(40);
        add(playPlusBtn1).minWidth(40).prefWidth(40);
        add(playTimeLabel).expandX().align(Align.right).padRight(20);
        row();
        add(playSlider).colspan(4).expandX().fillX();


        playSlider.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                float sliderValue=playSlider.getValue();
//                model.beeper.allNotesOff();
                model.nrModel.allNotesOffByEvents();
                model.gethData().setIndexByTime(sliderValue);
                model.nrModel.time.setTime(sliderValue);
                return true;
            }
        });

        model.nrModel.time.getPublisher().addListener(new IListener<Time>() {
            @Override
            public void on(Time event) {
                float time = model.nrModel.time.getTime();
                playTimeLabel.setText(String.format(Locale.UK, "%.0f /%.0f", time,totalTime));
                playSlider.setValue(time);
            }
        });
    }
}
