package com.pheminist;

public interface IVideoController {
    void startHRecord(String fileName);
    void stopHRecord();
    void addCameraView();
    void removeCameraView();
    void startCameraSession();

//    String getOutputPath();
}
