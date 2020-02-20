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
import com.pheminist.model.Shift;

import java.util.Locale;

import static com.pheminist.model.Model.SKIN;
import static com.pheminist.view.Hud.VPAD;

public class ShiftView extends Table{
    private Label shiftNumberLabel;
    private static final int shift = 0;

    public ShiftView(final Controller controller, final Model model) {
        final Skin skin=model.assetManager.get(SKIN, Skin.class);
        setSkin(skin);
        background("button-pressed");
        pad(VPAD, 10, VPAD, 10);
        //        shiftTable.setDebug(true);
        final Label shiftTextLabel = new Label("Shift", skin);
        final TextButton shiftMinusBtn = new TextButton("-", skin);
        shiftNumberLabel = new Label("", skin);
        setShiftLabel(shift);
        shiftNumberLabel.setAlignment(Align.center);
        TextButton shiftPlusBtn = new TextButton("+", skin);
        defaults().minHeight(40).prefHeight(40).pad(0, 5, 0, 5);
        add(shiftTextLabel).expandX();
        add(shiftMinusBtn).minWidth(40).prefWidth(40);
        add(shiftNumberLabel).minWidth(60);
        add(shiftPlusBtn).minWidth(40).prefWidth(40);

        model.shift.getPublisher().addListener(new IListener<Shift>() {
            @Override
            public void on(Shift event) {
                setShiftLabel(event.getShift());
            }
        });

        shiftMinusBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                controller.setShift(model.shift.getShift() - 1);
            }
        });

        shiftPlusBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                controller.setShift(model.shift.getShift() + 1);
            }
        });

    }

    private void setShiftLabel(int shift) {
        if (shift == 0) {
            shiftNumberLabel.setText("0");
            return;
        }
        shiftNumberLabel.setText(String.format(Locale.UK,"%+2d", shift));
    }

}
