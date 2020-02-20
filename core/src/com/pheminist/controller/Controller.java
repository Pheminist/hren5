package com.pheminist.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.ObjectMap;
import com.pheminist.model.Model;
import com.pheminist.view.BaseScreen;
import com.pheminist.view.FChooser;
import com.pheminist.view.GScreen;
import com.pheminist.view.TestScreen;

public class Controller extends Game {
    private ObjectMap<Class<? extends BaseScreen>, BaseScreen> screens = new ObjectMap<>();
    private Model model;


    @Override
    public void create() {
        model = new Model();

        // Load the screens
        loadScreens();
        this.changeScreen(FChooser.class);
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

    public void loadScreens() {
        screens.put(TestScreen.class, new TestScreen(this,model));
        screens.put(FChooser.class,new FChooser(this,model));
        screens.put(GScreen.class, new GScreen(this,model));
    }

    public void setTempo(float tempo){
        model.tempo.setTempo(tempo);
    }
    public void setShift(int shift) {model.shift.setShift(shift);}

    public void setQPS(float qps) {model.qps.setQps(qps);}

}
