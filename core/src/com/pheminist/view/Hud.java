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
import com.pheminist.model.Model;

import java.util.Locale;

import static com.pheminist.model.Model.SKIN;

public class Hud extends Table {
    private Controller controller;
    private Model model;
    private static final float VPAD = 20;
    private Skin skin;
    private float tempo = 1;
    private Label tempoNumberLabel;
    private int shift = 0;
    private Label shiftNumberLabel;
    private float qps = 4;
    private Label qpsNumberLabel;
    private Slider playSlider;
    private boolean sound = false;

    public Hud(Controller controller, Model model) {
        this.controller=controller;
        this.model=model;
        skin = model.assetManager.get(SKIN, Skin.class);
        show();
    }

    private void show() {
        Table shiftTable = new Table();
        shiftTable.setSkin(skin);
        shiftTable.background("button-pressed");
        shiftTable.pad(VPAD, 10, VPAD, 10);
//        shiftTable.setDebug(true);
        Label shiftTextLabel = new Label("Shift", skin);
        final TextButton shiftMinusBtn = new TextButton("-", skin);
        shiftNumberLabel = new Label("", skin);
        setShiftLabel();
        shiftNumberLabel.setAlignment(Align.center);
        TextButton shiftPlusBtn = new TextButton("+", skin);
        shiftTable.defaults().minHeight(40).prefHeight(40).pad(0, 5, 0, 5);
        shiftTable.add(shiftTextLabel).expandX();
        shiftTable.add(shiftMinusBtn).minWidth(40).prefWidth(40);
        shiftTable.add(shiftNumberLabel).minWidth(60);
        shiftTable.add(shiftPlusBtn).minWidth(40).prefWidth(40);

        Table tempoTable = new Table();
        Label tempoTextLabel = new Label("Tempo", skin);
        TextButton tempoMinusBtn = new TextButton("-", skin);
//        tempo=parent.getPreferences().getTempVolume();
        tempo=1f;
        tempoNumberLabel = new Label(String.format(Locale.UK, "%.1f", tempo), skin);
        tempoNumberLabel.setAlignment(Align.center);
//        tempoNumberLabel.setFontScale(0.5f);
        TextButton tempoPlusBtn = new TextButton("+", skin);
        final Slider tempoSlider = new Slider(0.4f, 2f, 0.1f, false, skin);
//        tempoSlider.setValue(parent.getPreferences().getTempVolume());
        tempoSlider.setValue(tempo);
        tempoTable.pad(VPAD, 10, VPAD, 10);
        tempoTable.setSkin(skin);
        tempoTable.background("button-pressed");
        tempoTable.defaults().minHeight(40).prefHeight(40).pad(0, 5, 0, 5);
        tempoTable.add(tempoTextLabel).expandX();
        tempoTable.add(tempoMinusBtn).minWidth(40).prefWidth(40);
        tempoTable.add(tempoNumberLabel).minWidth(60);
        tempoTable.add(tempoPlusBtn).minWidth(40).prefWidth(40);
        tempoTable.row();
        tempoTable.add(tempoSlider).colspan(4).expandX().fillX();
//        tempoTable.setDebug(true);

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
        sound=true;
        final TextButton soundBtn = new TextButton(sound?"Sound on":"Sound off", skin);
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
        this.add(shiftTable).expandX().fillX().uniformX();
        this.row().pad(0, 0, 0, 0);
        this.add(tempoTable).expandX().fillX().uniformX();
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

//        System.out.println("show hud");

        shiftMinusBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                shift--;
                shiftTone(shift);
            }
        });

        shiftPlusBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                shift++;
                shiftTone(shift);
            }
        });

        tempoMinusBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (tempo < 0.45) return;
                tempo -= 0.1f;
                tempoNumberLabel.setText(String.format(Locale.UK, "%.1f", tempo));
                tempoSlider.setValue(tempo);
            }
        });


        tempoPlusBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (tempo > 1.95) return;
                tempo += 0.1f;
                tempoNumberLabel.setText(String.format(Locale.UK, "%.1f", tempo));
                tempoSlider.setValue(tempo);
            }
        });

        tempoSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                tempo = tempoSlider.getValue();
                tempoNumberLabel.setText((String.format(Locale.UK, "%.1f", tempo)));
//                parent.getPreferences().setTempVolume(tempo);

            }
        });

        qpsMinusBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (qps < 1.5) return;
                qps -= 1f;
                qpsNumberLabel.setText(String.format(Locale.UK, "%.0f", qps));
//                controller.setQPS(qps);
//                gameScreen.setQuarterInScreen(qps);
            }
        });

        qpsPlusBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (qps > 19.5) return;
                qps += 1f;
                qpsNumberLabel.setText(String.format(Locale.UK, "%.0f", qps));
//                controller.setQPS(qps);
                //                gameScreen.setQuarterInScreen(qps);
            }
        });

        soundBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sound = !sound;
//                gameScreen.setSound(sound);
//                controller.setSound(sound);
                if (sound) {
                    soundBtn.setText("Sound on");
                }
                else soundBtn.setText("Sound off");
            }
        });


        playPlusBtn1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                parent.getPreferences().setPause(!playPlusBtn1.isChecked());
            }
        });



        //        newGame.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                if(parent.hData==null)
//                    parent.changeScreen(Controller.OPENFILE);
//                else {
//                    parent.hData.resetIndex();
//                    parent.changeScreen(Controller.APPLICATION);
//                }
//            }
//        });
//
        openFile.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                controller.changeScreen(Controller.OPENFILE);
                System.out.println("Why do I see this text when I am in OPENFILE screen");
            }
        });
//
//        soundBtn.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//
//
//                 parent.changeScreen(Controller.PREFERENCES);
//            }
//        });
//
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }

    private void setShiftLabel(){
        if (shift == 0) {
            shiftNumberLabel.setText("0");
            return;
        }
        shiftNumberLabel.setText(String.format("%+2d", shift));
    }

    private void shiftTone(int shift){
//        gameScreen.getBeeper().setShift(shift);
//        parent.getPreferences().setShift(shift);
//        gameScreen.getnButtonsRenderer().setNButtonsLabels();
        setShiftLabel();
    }
}
