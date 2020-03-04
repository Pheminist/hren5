package com.pheminist.view;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.SnapshotArray;
import com.pheminist.interfaces.IListener;
import com.pheminist.model.MIDI.HData;
import com.pheminist.model.MIDI.HFNoteHandler;
import com.pheminist.model.Model;
import com.pheminist.model.NoteEvent;
import com.pheminist.model.Shift;

public class NButtonsView extends Table implements IListener<NoteEvent> {
    Model model;
    HFNoteHandler hData;

    public NButtonsView(Model model) {
        this.model=model;
        hData = model.gethData();

        System.out.println(" get number of tones ======= "+hData.getnSoundes());

        for (int i=0;i<hData.getnSoundes();i++){
            NoteButton noteButton=new NoteButton(model,i);
            this.add(noteButton).expandX().fillX().uniform().minWidth(10);
        }

        model.noteEvent.getPublisher().addListener(this);

        model.shift.getPublisher().addListener(new IListener<Shift>() {
            @Override
            public void on(Shift event) {
                setNButtonsLabels();
            }
        });

    }

    public void setNButtonsLabels(){
        SnapshotArray<Actor> noteButtons=getChildren();
        for(int i=0;i<noteButtons.size;i++){
            ((NoteButton)noteButtons.get(i)).setToneLabel(hData.getTone(i)+model.shift.getShift());
        }
    }

    @Override
    public void on(NoteEvent event) {
        int note=event.getNote();
        boolean isOn=event.isOn();
        ((NoteButton)(getChildren().get(note))).setNote(isOn);

    }
}
