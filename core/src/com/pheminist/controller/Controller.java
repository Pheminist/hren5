package com.pheminist.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ObjectMap;
import com.pheminist.IVideoController;
import com.pheminist.interfaces.IHorner;
import com.pheminist.model.MIDI.HFNoteHandler;
import com.pheminist.model.MIDI.HNoteProvider;
import com.pheminist.model.MIDI.MidiData;
import com.pheminist.model.Model;
import com.pheminist.view.BaseScreen;
import com.pheminist.view.FChooser;
import com.pheminist.view.GScreen;
import com.pheminist.view.TestScreen;

public class Controller extends Game {
    private ObjectMap<Class<? extends BaseScreen>, BaseScreen> screens = new ObjectMap<>();
    private Model model;
    private IHorner horner;
    private IVideoController videoController;

    public Controller(IHorner horner, IVideoController videoController) {
        this.horner=horner;
        this.videoController=videoController;
    }

    @Override
    public void create() {
        model = new Model(horner);

        // Load the screens
        loadScreens();
//        this.changeScreen(FChooser.class);
//        model.sethData(new HFNoteHandler(new HNoteProvider(new MidiData(Gdx.files.internal("melodies/OldMaple.mid")))));
        this.changeScreen(GScreen.class);
    }


    @Override
    public void dispose() {
        // Dispose of the view
        setScreen(null);

        for (BaseScreen screen : screens.values()) {
            screen.dispose();
        }
        screens.clear();

        // Dispose of the model
        model.dispose();
    }

    // === Screen Management === //
    public void changeScreen(Class<? extends BaseScreen> key) {
        this.setScreen(screens.get(key));
//		handle(new GameEvent("SCREEN_CHANGE").set("SCREEN", screens.get(key)));
    }

    private void loadScreens() {
//        screens.put(TestScreen.class, new TestScreen(this,model));
        screens.put(FChooser.class,new FChooser(this,model));
        screens.put(GScreen.class, new GScreen(this,model));
    }

    public void setTempo(float tempo){
        model.tempo.setTempo(tempo);
    }
    public void setShift(int shift) {model.shift.setShift(shift);}

    public void setSPS(float qps) {model.sps.setSps(qps);}

    public void startRecord(String fileName){
        videoController.startHRecord(fileName);
    }

    public void stopRecord(){
        videoController.stopHRecord();
    }

    public void removeCameraView(){
        videoController.removeCameraView();

    }

    public void addCameraView(){
        videoController.addCameraView();
    }
}
