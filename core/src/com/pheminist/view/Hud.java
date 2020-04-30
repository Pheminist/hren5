package com.pheminist.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.pheminist.controller.Controller;
import com.pheminist.interfaces.IListener;
import com.pheminist.model.Model;
import com.pheminist.model.NRState;
import com.pheminist.view.menu.PlayView;
import com.pheminist.view.menu.RecButton;
import com.pheminist.view.menu.SPSView;
import com.pheminist.view.menu.ShiftView;
import com.pheminist.view.menu.TempoView;

import static com.pheminist.model.Model.SKIN;

public class Hud extends Table {
    public static final float VPAD = 10;
    private Controller controller;
    private Model model;
    private Skin skin;
    private boolean sound = false;

    public final RecButton recBtn;
    public final PlayView playView;

    Hud(Controller contr, Model mod) {
        this.controller = contr;
        this.model = mod;
        skin = model.assetManager.get(SKIN, Skin.class);

        recBtn = new RecButton(controller, model);
        recBtn.pad(VPAD, 0, VPAD, 0);
        TextButton openFile = new TextButton("Open", skin);
        openFile.pad(VPAD, 0, VPAD, 0);
//        sound=parent.getPreferences().isMusicEnabled();
        sound = true;
        final TextButton soundBtn = new TextButton(sound ? "Sound on" : "Sound off", skin);
        soundBtn.pad(VPAD, 0, VPAD, 0);
        TextButton exit = new TextButton("Exit", skin);
        exit.pad(VPAD, 0, VPAD, 0);

        playView=new PlayView(controller, model);

        this.defaults().expandY().fillY().growY().expandX().fillX().uniformX();                                     ///////
        this.add(recBtn);
        this.row();
        this.add(new ShiftView(controller, model));
        this.row();
        this.add(new TempoView(controller, model));
        this.row();
        this.add(new SPSView(controller, model));
        this.row();
        this.add(soundBtn);
        this.row();
        this.add(openFile);
        this.row();
        this.add(exit);
        this.row();
        this.add(playView).uniform(false);

        soundBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sound = !sound;
                if (sound) {
                    soundBtn.setText("Sound on");
                    model.noteEvent.getPublisher().addListener(model.beeper);
                } else {
                    soundBtn.setText("Sound off");
                    model.noteEvent.getPublisher().removeListener(model.beeper);
                    model.beeper.allNotesOff();
                }
            }
        });

        openFile.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                model.beeper.allNotesOff();
                controller.changeScreen(FChooser.class);
            }
        });

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }
}
