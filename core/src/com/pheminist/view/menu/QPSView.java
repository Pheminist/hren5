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
import com.pheminist.model.QPS;

import java.util.Locale;

import static com.pheminist.model.Model.SKIN;
import static com.pheminist.view.Hud.VPAD;

public class QPSView extends Table {
    private static final float qps = 4;
    private final Label qpsNumberLabel;

    public QPSView(final Controller controller, final Model model) {
        final Skin skin = model.assetManager.get(SKIN, Skin.class);

        setSkin(skin);
        background("button-pressed");
        pad(VPAD, 10, VPAD, 10);
        Label qpsTextLabel = new Label("Quarters per screen", skin);
        qpsTextLabel.setWrap(true);
        qpsTextLabel.setAlignment(Align.center);
        TextButton qpsMinusBtn = new TextButton("-", skin);
        qpsNumberLabel = new Label(String.format(Locale.UK, "%.0f", qps), skin);
        qpsNumberLabel.setAlignment(Align.center);
        TextButton qpsPlusBtn = new TextButton("+", skin);
        defaults().pad(0, 5, 0, 5);
//        qpsTextLabel.setFontScale(0.7f);
        add(qpsTextLabel).expandX().fillX();//.colspan(3);
        defaults().minHeight(40).prefHeight(40);
        add(qpsMinusBtn).minWidth(40).prefWidth(40);
        add(qpsNumberLabel).minWidth(50);
        add(qpsPlusBtn).minWidth(40).prefWidth(40);

        model.qps.getPublisher().addListener(new IListener<QPS>() {
            @Override
            public void on(QPS event) {
                setQPSLabel(model.qps.getQps());
            }
        });

        qpsMinusBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.setQPS(model.qps.getQps()-1f);
            }
        });

        qpsPlusBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.setQPS(model.qps.getQps()+1f);
            }
        });
    }

    private void setQPSLabel(float qps){
        qpsNumberLabel.setText(String.format(Locale.UK, "%.0f", qps));
    }

}
