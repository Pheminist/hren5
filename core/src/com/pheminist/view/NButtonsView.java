package com.pheminist.view;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.SnapshotArray;
import com.pheminist.interfaces.IListener;
import com.pheminist.model.MIDI.HData;
import com.pheminist.model.Model;
import com.pheminist.model.NoteEvent;

public class NButtonsView extends Table implements IListener<NoteEvent> {
    HData hData;
//    Hren parent;

    public NButtonsView(Model model) {
        hData = model.gethData();
//        this.parent = parent;
//        Skin skin = parent.assets.get(Hren.SKIN, Skin.class);

        for (int i=0;i<hData.getnTones();i++){
//            TextButton noteButton=new TextButton(HData.octaveAndNoteName(hData.getTones()[i]),skin);
//            noteButton.getLabel().setFontScale(noteButton.getLabel().getScaleX());
            NoteButton noteButton=new NoteButton(model,i);
            this.add(noteButton).expandX().fillX().uniform().minWidth(10);
        }

    }

//    @Override
//    public void noteEvent(int note, int tone, boolean isOn) {
//        ((NoteButton)(getChildren().get(note))).setNote(isOn);
//
//    }

    public void setNButtonsLabels(){
        SnapshotArray<Actor> noteButtons=getChildren();
        for(int i=0;i<noteButtons.size;i++){
//            ((NoteButton)noteButtons.get(i)).setToneLabel(hData.getTones()[i]+parent.getPreferences().getShift());
            ((NoteButton)noteButtons.get(i)).setToneLabel(hData.getTones()[i]);
        }


    }

    @Override
    public void on(NoteEvent event) {
        int note=event.getNote();
        boolean isOn=event.isOn();
        ((NoteButton)(getChildren().get(note))).setNote(isOn);

    }
}
