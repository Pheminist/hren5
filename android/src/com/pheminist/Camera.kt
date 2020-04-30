package com.pheminist

import android.hardware.camera2.CameraCharacteristics
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.camera.core.VideoCapture
import androidx.camera.view.CameraView
import androidx.core.content.ContextCompat
import com.pheminist.interfaces.IListener
import com.pheminist.model.Pause
import java.io.File

class Camera(private val context: AndroidLauncher):IVideoController {
    val cameraView: CameraView = CameraView(context)
    private val pauseListener = IListener<Pause>{
        if(it.isPaused) stopHRecord();
    }
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

    override fun getPauseListener(): IListener<Pause> {
        return pauseListener

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
}