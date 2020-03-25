package com.pheminist.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pheminist.model.Model;
import com.pheminist.utils.HUtils;

import static com.pheminist.model.Model.SKIN;

public class NoteButton extends Table {
//    private Hren parent;
//    private HData hData;
    private Label label;
    private final int note;
    private Model model;

    private boolean isAlive=true;

    public NoteButton(Model model,int note) {
        this.model = model;
        this.note = note;
        Skin skin = model.assetManager.get(SKIN, Skin.class);

        this.setBackground(skin.getDrawable("knobwhite"));
        label=new Label(HUtils.octaveAndNoteName(model.gethData().getTone(note)+model.shift.getShift()),skin);
        label.setColor(NR.getNoteColor(note));
        this.add(label);
        this.setColor(Color.DARK_GRAY);

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setAlive(!isAlive());
            }
        });

        //        setSize(30,20);
//        super(HData.octaveAndNoteName(hData.getTones()[note]), skin);
//        textButton.getLabel().
    }

    private float prefHeight =10;
    @Override
    protected void sizeChanged() {
        super.sizeChanged();
        float scale=getWidth()/70;
        label.setFontScale(scale);

        prefHeight =getWidth()*0.7f;
//        invalidate();
//        invalidateHierarchy();

        System.out.println("noteButton changed size");
    }

    public void setNote(boolean isOn){
        if(isOn){
            setColor(Color.WHITE);
        }
        else {
            setColor(Color.DARK_GRAY);
        }
    }

    public void setToneLabel(int tone){
        label.setText(HUtils.octaveAndNoteName(tone));
    }

    @Override
    public float getPrefWidth() {
        return 50f;
    }

    @Override
    public float getMinHeight() {
        return prefHeight;
    }

    @Override
    public float getPrefHeight() {
        return prefHeight;//50f;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
        model.nrModel.setNoteAlive(note,isAlive);

        if(!isAlive) label.setColor(Color.DARK_GRAY);
        else label.setColor(NR.getNoteColor(note));
    }

}
