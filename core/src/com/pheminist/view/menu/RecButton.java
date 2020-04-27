package com.pheminist.view.menu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.pheminist.controller.Controller;
import com.pheminist.model.Model;

import static com.pheminist.model.Model.SKIN;

public class RecButton extends TextButton {
    private final static String REC_OFF = "Start record";
    private final static String REC_ON  = "Stop record";
    private boolean record = false;

    public RecButton(final Controller controller, final Model model) {
        super(REC_OFF, model.assetManager.get(SKIN, Skin.class));

        addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!record) {
                    record=true;
                    model.nrModel.setPaused(true);
                    model.nrModel.allNotesOffByEvents();
                    model.gethData().setIndexByTime(-2f);
                    model.nrModel.time.setTime(-2);
                    getLabel().setText(REC_ON);

                    String fileName = model.inputFile.getBareName() + model.nrModel.getDeadNotes() + ".mp4";
//                    File file = new File("C:\\user\\Desktop\\dir1\\dir2\\filename.txt");
//                    file.getParentFile().mkdirs();
//                    FileWriter writer = new FileWriter(file);
                    controller.startRecord(fileName);
                    model.nrModel.setPaused(false);
                    setChecked(true);
                }
                else {
                    record=false;
                    getLabel().setText(REC_OFF);
                    model.nrModel.setPaused(true);
                    controller.stopRecord();
                    setChecked(false);
                }
            }
        });

    }
}
