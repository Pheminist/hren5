package com.pheminist.view.menu;

import com.badlogic.gdx.scenes.scene2d.Actor;
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
import com.pheminist.model.Tempo;

import java.util.Locale;

import static com.pheminist.model.Model.SKIN;
import static com.pheminist.view.Hud.VPAD;

public class TempoView extends Table{
    private Label tempoNumberLabel;
    private static final float tempo = 1;

    public TempoView(final Controller controller,final Model model) {
        final Skin skin = model.assetManager.get(SKIN, Skin.class);

//        Table tempoTable = new Table();
        Label tempoTextLabel = new Label("Tempo", skin);
        TextButton tempoMinusBtn = new TextButton("-", skin);
//        tempo=parent.getPreferences().getTempVolume();
        tempoNumberLabel = new Label(String.format(Locale.UK, "%.1f", tempo), skin);
        tempoNumberLabel.setAlignment(Align.center);
//        tempoNumberLabel.setFontScale(0.5f);
        TextButton tempoPlusBtn = new TextButton("+", skin);
        final Slider tempoSlider = new Slider(Tempo.minTempo, Tempo.maxTempo,
                0.1f, false, skin);
//        tempoSlider.setValue(parent.getPreferences().getTempVolume());
        tempoSlider.setValue(tempo);
        pad(VPAD, 10, VPAD, 10);
        setSkin(skin);
        background("button-pressed");
        defaults().minHeight(40).prefHeight(40).pad(0, 5, 0, 5);
        add(tempoTextLabel).expandX();
        add(tempoMinusBtn).minWidth(40).prefWidth(40);
        add(tempoNumberLabel).minWidth(60);
        add(tempoPlusBtn).minWidth(40).prefWidth(40);
        row();
        add(tempoSlider).colspan(4).expandX().fillX();
//        tempoTable.setDebug(true);

        model.tempo.getPublisher().addListener(new IListener<Tempo>() {
            @Override
            public void on(Tempo event) {
                float tempo = event.getTempo();
                tempoNumberLabel.setText(String.format(Locale.UK, "%.1f", tempo));
                tempoSlider.setValue(tempo);
            }
        });

        tempoMinusBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                controller.setTempo(model.tempo.getTempo() - 0.1f);
            }
        });

        tempoPlusBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                controller.setTempo(model.tempo.getTempo() + 0.1f);
            }
        });

        tempoSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                controller.setTempo(tempoSlider.getValue());
            }
        });
    }
}
