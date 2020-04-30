package com.pheminist.desktop;

import com.pheminist.IVideoController;
import com.pheminist.interfaces.IListener;
import com.pheminist.model.Pause;

public class VideoController implements IVideoController {
    private IListener<Pause> pauseListener=new IListener<Pause>() {
        @Override
        public void on(Pause event) {

        }
    };

    @Override
    public void startHRecord(String fileName) {

    }

    @Override
    public void stopHRecord() {

    }

    @Override
    public void addCameraView() {

    }

    @Override
    public void removeCameraView() {

    }

    @Override
    public void startCameraSession() {

    }

    @Override
    public IListener<Pause> getPauseListener() {
        return pauseListener;
    }
}
