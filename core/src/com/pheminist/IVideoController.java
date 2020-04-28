package com.pheminist;

import com.pheminist.interfaces.IListener;
import com.pheminist.model.NRState;

public interface IVideoController extends IListener<NRState> {
    void startHRecord(String fileName);
    void stopHRecord();
    void addCameraView();
    void removeCameraView();
    void startCameraSession();

//    String getOutputPath();
}
