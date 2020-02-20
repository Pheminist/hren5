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
import com.pheminist.view.menu.ShiftView;
import com.pheminist.view.menu.TempoView;

import java.util.Locale;

import static com.pheminist.model.Model.SKIN;

public class Hud extends Table {
    public static final float VPAD = 20;
    private Controller controller;
    private Model model;
    private Skin skin;
    private static final float qps = 4;
    private Label qpsNumberLabel;
    private Slider playSlider;
    private boolean sound = false;

    public Hud(Controller controller, Model model) {
        this.controller = controller;
        this.model = model;
        skin = model.assetManager.get(SKIN, Skin.class);
        show();
    }

    private void show() {
        Table qpsTable = new Table();
        qpsTable.setSkin(skin);
        qpsTable.background("button-pressed");
        qpsTable.pad(VPAD, 10, VPAD, 10);
        Label qpsTextLabel = new Label("Quarters per screen", skin);
        qpsTextLabel.setWrap(true);
        qpsTextLabel.setAlignment(Align.center);
        TextButton qpsMinusBtn = new TextButton("-", skin);
        qpsNumberLabel = new Label(String.format(Locale.UK, "%.0f", qps), skin);
        qpsNumberLabel.setAlignment(Align.center);
        TextButton qpsPlusBtn = new TextButton("+", skin);
        qpsTable.defaults().pad(0, 5, 0, 5);
//        qpsTextLabel.setFontScale(0.7f);
        qpsTable.add(qpsTextLabel).expandX().fillX();//.colspan(3);
        qpsTable.defaults().minHeight(40).prefHeight(40);
        qpsTable.add(qpsMinusBtn).minWidth(40).prefWidth(40);
        qpsTable.add(qpsNumberLabel).minWidth(50);
        qpsTable.add(qpsPlusBtn).minWidth(40).prefWidth(40);

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

        Table playTable = new Table();
        TextButton playMinusBtn = new TextButton("-", skin);
        TextButton playPlusBtn = new TextButton("+", skin);
        final ImageButton playPlusBtn1 = new ImageButton(skin, "play");
        playPlusBtn1.setChecked(true);
//        TextButton playPlusBtn2 =new TextButton("+",skin);
//        TextButton playPlusBtn3 =new TextButton("+",skin);
        Label playTimeLabel = new Label("44:44/44:44", skin);
        playTimeLabel.setFontScale(0.8f);
//        playSlider = new Slider(0f, parent.hData.getTotalTicks(), 0.01f, false, skin);
        playSlider = new Slider(0f, 200f, 0.01f, false, skin);
        playTable.pad(VPAD, 10, VPAD, 10);
//        tempoTable.setDebug(true);
        playTable.setSkin(skin);
        playTable.background("button-pressed");
        playTable.defaults().minHeight(40).prefHeight(40).pad(0, 2, 0, 2);
        playTable.add(playMinusBtn).minWidth(40).prefWidth(40);
        playTable.add(playPlusBtn).minWidth(40).prefWidth(40);
        playTable.add(playPlusBtn1).minWidth(40).prefWidth(40);
//        playTable.add(playPlusBtn2).minWidth(40).prefWidth(40);
//        playTable.add(playPlusBtn3).minWidth(40).prefWidth(40);
        playTable.add(playTimeLabel).expandX();
        playTable.row();
        playTable.add(playSlider).colspan(4).expandX().fillX();

        this.add(newGame).expandX().fillX();
        this.defaults().expandY().fillY().growY();                                     ///////
        this.row().pad(0, 0, 0, 0);
        this.add(new ShiftView(controller,model)).expandX().fillX().uniformX();
        this.row().pad(0, 0, 0, 0);
        this.add(new TempoView(controller,model)).expandX().fillX().uniformX();
        this.row().pad(0, 0, 0, 0);
        this.add(qpsTable).expandX().fillX().uniformX();
        this.row().pad(0, 0, 0, 0);
        this.add(soundBtn).expandX().fillX().uniformX();
        this.row().pad(0, 0, 0, 0);
        this.add(openFile).minWidth(100).fillX().uniformX();
        this.row().pad(0, 0, 0, 0);
        this.add(exit).minWidth(100).fillX().uniformX();
        this.row();
        this.add(playTable).expandX().fillX();

        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
        });

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


        playPlusBtn1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                parent.getPreferences().setPause(!playPlusBtn1.isChecked());
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

    private void setQPSLabel(float qps){
        qpsNumberLabel.setText(String.format(Locale.UK, "%.0f", qps));
    }
}
