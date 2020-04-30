package com.pheminist;

import com.pheminist.interfaces.IListener;
import com.pheminist.model.Pause;

public interface IVideoController {
    void startHRecord(String fileName);
    void stopHRecord();
    void addCameraView();
    void removeCameraView();
    void startCameraSession();
    IListener<Pause> getPauseListener();

//    String getOutputPath();
}
