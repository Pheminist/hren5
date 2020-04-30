package com.pheminist.view.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.pheminist.controller.Controller;
import com.pheminist.interfaces.IListener;
import com.pheminist.model.Model;
import com.pheminist.model.Pause;

import static com.pheminist.model.Model.SKIN;

public class RecButton extends TextButton {
    private final static String REC_OFF = "Start record";
    private final static String REC_ON = "Stop record";
    private final Color offColor;
    private Controller controller;
    private Model model;
    private boolean record = false;
    private final IListener<Pause> pauseIListener = new IListener<Pause>() {
        @Override
        public void on(Pause event) {
            if (event.isPaused()) {
                record = false;
                getLabel().setText(REC_OFF);
                getLabel().setColor(offColor);
                controller.stopRecord();
            }
        }
    };

    public RecButton(final Controller controller, final Model model) {
        super(REC_OFF, model.assetManager.get(SKIN, Skin.class));
        this.controller = controller;
        this.model = model;
        offColor = new Color(getLabel().getColor());

        addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!record) {
                    model.pause.setPause(true);
                    record = true;
                    model.nrModel.allNotesOffByEvents();
                    model.gethData().setIndexByTime(-2f);
                    model.time.setTime(-2);
                    getLabel().setText(REC_ON);
                    getLabel().setColor(Color.RED);

                    String fileName = model.inputFile.getBareName() + model.nrModel.getDeadNotes() + ".mp4";
//                    File file = new File("C:\\user\\Desktop\\dir1\\dir2\\filename.txt");
//                    file.getParentFile().mkdirs();
//                    FileWriter writer = new FileWriter(file);
                    controller.startRecord(fileName);
                    model.pause.setPause(false);
//                    setChecked(true);
                } else {
                    model.pause.setPause(true);
//                    setChecked(false);
                }
            }
        });

    }

    public IListener<Pause> getPauseListener() {
        return pauseIListener;
    }
}
