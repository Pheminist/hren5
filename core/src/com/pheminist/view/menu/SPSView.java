package com.pheminist.view.menu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.pheminist.controller.Controller;
import com.pheminist.interfaces.IListener;
import com.pheminist.model.Model;
import com.pheminist.model.SecondPerScreen;

import java.util.Locale;

import static com.pheminist.model.Model.SKIN;
import static com.pheminist.view.Hud.VPAD;

public class SPSView extends Table {
    private final Label qpsNumberLabel;

    public SPSView(final Controller controller, final Model model) {
        final Skin skin = model.assetManager.get(SKIN, Skin.class);

        setSkin(skin);
        background("button-pressed");
        pad(VPAD, 10, VPAD, 10);
        Label qpsTextLabel = new Label("Seconds per screen", skin);
        qpsTextLabel.setWrap(true);
        qpsTextLabel.setAlignment(Align.center);
        TextButton qpsMinusBtn = new TextButton("-", skin);
        qpsNumberLabel = new Label(String.format(Locale.UK, "%.1f", model.sps.getSps()), skin);
        qpsNumberLabel.setAlignment(Align.center);
        TextButton qpsPlusBtn = new TextButton("+", skin);
        defaults().pad(0, 5, 0, 5);
//        qpsTextLabel.setFontScale(0.7f);
        add(qpsTextLabel).expandX().fillX();
        defaults().minHeight(40).prefHeight(40);
        add(qpsMinusBtn).minWidth(40).prefWidth(40);
        add(qpsNumberLabel).minWidth(50);
        add(qpsPlusBtn).minWidth(40).prefWidth(40);

        model.sps.getPublisher().addListener(new IListener<SecondPerScreen>() {
            @Override
            public void on(SecondPerScreen event) {
                setQPSLabel(model.sps.getSps());
            }
        });

        qpsMinusBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.setSPS(model.sps.getSps()-0.5f);
            }
        });

        qpsPlusBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.setSPS(model.sps.getSps()+0.5f);
            }
        });
    }

    private void setQPSLabel(float sps){
        qpsNumberLabel.setText(String.format(Locale.UK, "%.1f", sps));
    }

}
