package com.pheminist.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.pheminist.controller.Controller;
import com.pheminist.model.Model;
import com.pheminist.view.menu.PlayView;
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

    Hud(Controller controller, Model model) {
        this.controller = controller;
        this.model = model;
        skin = model.assetManager.get(SKIN, Skin.class);
        show();
    }

    private void show() {
        TextButton helpBtn = new TextButton("Help", skin);
        helpBtn.pad(VPAD, 0, VPAD, 0);
        TextButton openFile = new TextButton("Open", skin);
        openFile.pad(VPAD, 0, VPAD, 0);
//        sound=parent.getPreferences().isMusicEnabled();
        sound = true;
        final TextButton soundBtn = new TextButton(sound ? "Sound on" : "Sound off", skin);
        soundBtn.pad(VPAD, 0, VPAD, 0);
        TextButton exit = new TextButton("Exit", skin);
        exit.pad(VPAD, 0, VPAD, 0);

        this.defaults().expandY().fillY().growY().expandX().fillX().uniformX();                                     ///////
        this.add(helpBtn);
        this.row();
        this.add(new ShiftView(controller,model));
        this.row();
        this.add(new TempoView(controller,model));
        this.row();
        this.add(new SPSView(controller,model));
        this.row();
        this.add(soundBtn);
        this.row();
        this.add(openFile);
        this.row();
        this.add(exit);
        this.row();
        this.add(new PlayView(controller,model)).uniform(false);

        helpBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.startRecord();
            }
        });

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
