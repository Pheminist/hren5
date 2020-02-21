package com.pheminist.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
import com.pheminist.model.QPS;
import com.pheminist.model.Shift;
import com.pheminist.model.Tempo;
import com.pheminist.view.menu.PlayView;
import com.pheminist.view.menu.QPSView;
import com.pheminist.view.menu.ShiftView;
import com.pheminist.view.menu.TempoView;

import java.util.Locale;

import static com.pheminist.model.Model.SKIN;

public class Hud extends Table {
    public static final float VPAD = 20;
    private Controller controller;
    private Model model;
    private Skin skin;
    private Slider playSlider;
    private boolean sound = false;

    Hud(Controller controller, Model model) {
        this.controller = controller;
        this.model = model;
        skin = model.assetManager.get(SKIN, Skin.class);
        show();
    }

    private void show() {
        TextButton newGame = new TextButton("New", skin);
        newGame.pad(VPAD, 0, VPAD, 0);
        TextButton openFile = new TextButton("Open", skin);
        openFile.pad(VPAD, 0, VPAD, 0);
//        sound=parent.getPreferences().isMusicEnabled();
        sound = true;
        final TextButton soundBtn = new TextButton(sound ? "Sound on" : "Sound off", skin);
        soundBtn.pad(VPAD, 0, VPAD, 0);
        TextButton exit = new TextButton("Exit", skin);
        exit.pad(VPAD, 0, VPAD, 0);

        this.add(newGame).expandX().fillX();
        this.defaults().expandY().fillY().growY();                                     ///////
        this.row().pad(0, 0, 0, 0);
        this.add(new ShiftView(controller,model)).expandX().fillX().uniformX();
        this.row().pad(0, 0, 0, 0);
        this.add(new TempoView(controller,model)).expandX().fillX().uniformX();
        this.row().pad(0, 0, 0, 0);
        this.add(new QPSView(controller,model)).expandX().fillX().uniformX();
        this.row().pad(0, 0, 0, 0);
        this.add(soundBtn).expandX().fillX().uniformX();
        this.row().pad(0, 0, 0, 0);
        this.add(openFile).minWidth(100).fillX().uniformX();
        this.row().pad(0, 0, 0, 0);
        this.add(exit).minWidth(100).fillX().uniformX();
        this.row();
        this.add(new PlayView(controller,model)).expandX().fillX();

        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
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
//                controller.changeScreen(Controller.OPENFILE);
                System.out.println("Why do I see this text when I am in OPENFILE screen");
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
