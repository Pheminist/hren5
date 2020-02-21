package com.pheminist.view.menu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.pheminist.controller.Controller;
import com.pheminist.model.Model;

import static com.pheminist.model.Model.SKIN;
import static com.pheminist.view.Hud.VPAD;

public class PlayView extends Table {
    public PlayView(final Controller controller, final Model model) {
        final Skin skin=model.assetManager.get(SKIN,Skin.class);
        TextButton playMinusBtn = new TextButton("-", skin);
        TextButton playPlusBtn = new TextButton("+", skin);
        final ImageButton playPlusBtn1 = new ImageButton(skin, "play");
        playPlusBtn1.setChecked(true);
        Label playTimeLabel = new Label("44:44/44:44", skin);
        playTimeLabel.setFontScale(0.8f);
//        playSlider = new Slider(0f, parent.hData.getTotalTicks(), 0.01f, false, skin);
        final Slider playSlider = new Slider(0f, 200f, 0.01f, false, skin);
        pad(VPAD, 10, VPAD, 10);
//        tempoTable.setDebug(true);
        setSkin(skin);
        background("button-pressed");
        defaults().minHeight(40).prefHeight(40).pad(0, 2, 0, 2);
        add(playMinusBtn).minWidth(40).prefWidth(40);
        add(playPlusBtn).minWidth(40).prefWidth(40);
        add(playPlusBtn1).minWidth(40).prefWidth(40);
        add(playTimeLabel).expandX();
        row();
        add(playSlider).colspan(4).expandX().fillX();

        playPlusBtn1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                parent.getPreferences().setPause(!playPlusBtn1.isChecked());
            }
        });
    }
}
