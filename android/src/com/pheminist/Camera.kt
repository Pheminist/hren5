package com.pheminist

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraCharacteristics
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.VideoCapture
import androidx.camera.view.CameraView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.pheminist.interfaces.IListener
import com.pheminist.model.NRState
import java.io.File

class Camera(private val context: AndroidLauncher):IVideoController {
    val cameraView: CameraView = CameraView(context)
    private var isRecording = false
    private var videoRecordingPath: String
    val TAG = AndroidLauncher::class.java.simpleName

    init {
        cameraView.layoutParams = FrameLayout.LayoutParams(200, 100)
        cameraView.scaleType = CameraView.ScaleType.CENTER_INSIDE
        cameraView.captureMode = CameraView.CaptureMode.VIDEO
        cameraView.cameraLensFacing = CameraCharacteristics.LENS_FACING_FRONT

        // Part from CameraX
        val recordFiles = ContextCompat.getExternalFilesDirs(context, Environment.DIRECTORY_MOVIES)
        val storageDirectory = recordFiles[0]
        videoRecordingPath = "${storageDirectory.absoluteFile}/"
    }

    override fun startCameraSession() {
        cameraView.bindToLifecycle(context)
    }

    private fun recordVideo(videoRecordingFilePath: String) {
        cameraView.startRecording(File(videoRecordingFilePath), ContextCompat.getMainExecutor(context), object : VideoCapture.OnVideoSavedCallback {
            override fun onVideoSaved(file: File) {
                Toast.makeText(context, "Recording Saved", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "onVideoSaved $videoRecordingFilePath")
            }

            override fun onError(videoCaptureError: Int, message: String, cause: Throwable?) {
//                Toast.makeText(this@MainActivity, "Recording Failed", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "onError $videoCaptureError $message")
            }
        })
    }

    override fun stopHRecord() {
        if (isRecording) {
            isRecording = false
//            video_record.text = "Record Video"
//            runOnUiThread {Toast.makeText(this, "Recording Stopped", Toast.LENGTH_SHORT).show()}
            cameraView.stopRecording()
        }
    }

    override fun startHRecord(fileName: String?) {
        if (!isRecording) {
            isRecording = true
            recordVideo(videoRecordingPath + fileName)
        }
    }

    override fun removeCameraView() {
        context.runOnUiThread {
//            layout.removeView(cameraView)
            cameraView.visibility = View.GONE
        }
    }

    override fun addCameraView() {
        context.runOnUiThread {
//            layout.addView(cameraView)
            cameraView.visibility = View.VISIBLE
        }
    }

    override fun on(event: NRState?) {
        if(event?.state==NRState.PAUSED) stopHRecord();
    }

}