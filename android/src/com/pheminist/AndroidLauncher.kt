package com.pheminist

import android.Manifest
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.hardware.camera2.CameraCharacteristics
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.VideoCapture
import androidx.camera.view.CameraView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.pheminist.controller.Controller
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class AndroidLauncher : AndroidApplication(), LifecycleOwner, IVideoController {
    private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)
    val TAG = AndroidLauncher::class.java.simpleName
    private var isRecording = false

    private var CAMERA_PERMISSION = Manifest.permission.CAMERA
    private var RECORD_AUDIO_PERMISSION = Manifest.permission.RECORD_AUDIO

    private var RC_PERMISSION = 101

    lateinit var cameraView:CameraView
    private lateinit var videoRecordingPath:String

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }

    public override fun onStart() {
        super.onStart()
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()
        val gdxView = initializeForView(Controller(AndroidHorner(),this), config)
        val context = context
        val inflater = LayoutInflater.from(context)
        val layout = RelativeLayout(this)
        layout.addView(gdxView)
        //		CameraView cameraView = new CameraView(this);
//		cameraView.setLayoutParams(new FrameLayout.LayoutParams(100,100));

//		ConstraintLayout cameraView = (ConstraintLayout) inflater.inflate(R.layout.camera_view,null,false);
//		layout.addView(cameraView);
        cameraView = CameraView(this)
        cameraView.layoutParams = FrameLayout.LayoutParams(200, 100)
        cameraView.scaleType = CameraView.ScaleType.CENTER_INSIDE
        cameraView.captureMode = CameraView.CaptureMode.VIDEO
        cameraView.cameraLensFacing = CameraCharacteristics.LENS_FACING_FRONT
        layout.addView(cameraView)
//        cameraView.bindToLifecycle(this)

        setContentView(layout)

        // Part from CameraX
        val recordFiles = ContextCompat.getExternalFilesDirs(this, Environment.DIRECTORY_MOVIES)
        val storageDirectory = recordFiles[0]
//        val videoRecordingFilePath = "${storageDirectory.absoluteFile}/${System.currentTimeMillis()}_video.mp4"
        videoRecordingPath = "${storageDirectory.absoluteFile}/"

        if (checkPermissions()) startCameraSession()  else requestPermissions()

//        video_record.setOnClickListener {
//            if (isRecording) {
//                isRecording = false
//                video_record.text = "Record Video"
//                Toast.makeText(this, "Recording Stopped", Toast.LENGTH_SHORT).show()
//                camera_view.stopRecording()
//            } else {
//                isRecording = true
//                video_record.text = "Stop Recording"
//                Toast.makeText(this, "Recording Started", Toast.LENGTH_SHORT).show()
//                recordVideo(videoRecordingPath+)
//            }
//        }


    }

    init {
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
    }



    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(CAMERA_PERMISSION, RECORD_AUDIO_PERMISSION), RC_PERMISSION)
    }

    private fun checkPermissions(): Boolean {
        return ((ActivityCompat.checkSelfPermission(this, CAMERA_PERMISSION)) == PackageManager.PERMISSION_GRANTED
                && (ActivityCompat.checkSelfPermission(this, CAMERA_PERMISSION)) == PackageManager.PERMISSION_GRANTED)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            RC_PERMISSION -> {
                var allPermissionsGranted = false
                for (result in grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        allPermissionsGranted = false
                        break
                    } else {
                        allPermissionsGranted = true
                    }
                }
                if (allPermissionsGranted) startCameraSession() else permissionsNotGranted()
            }
        }
    }

    private fun startCameraSession() {
//        camera_view.bindToLifecycle(this)
        cameraView.bindToLifecycle(this)
    }

    private fun permissionsNotGranted() {
        AlertDialog.Builder(this).setTitle("Permissions required")
                .setMessage("These permissions are required to use this app. Please allow Camera and Audio permissions first")
                .setCancelable(false)
                .setPositiveButton("Grant") { dialog, which -> requestPermissions() }
                .show()
    }

    private fun recordVideo(videoRecordingFilePath: String) {
        camera_view.startRecording(File(videoRecordingFilePath), ContextCompat.getMainExecutor(this), object: VideoCapture.OnVideoSavedCallback {
            override fun onVideoSaved(file: File) {
//                Toast.makeText(this@AndroidLancher, "Recording Saved", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "onVideoSaved $videoRecordingFilePath")
            }

            override fun onError(videoCaptureError: Int, message: String, cause: Throwable?) {
//                Toast.makeText(this@MainActivity, "Recording Failed", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "onError $videoCaptureError $message")
            }
        })
    }















    override fun stopHRecord() {
        TODO("Not yet implemented")
    }

    override fun startHRecord(fileName: String?) {
        if (isRecording) {
            isRecording = false
            video_record.text = "Record Video"
            Toast.makeText(this, "Recording Stopped", Toast.LENGTH_SHORT).show()
            camera_view.stopRecording()
        } else {
            isRecording = true
            video_record.text = "Stop Recording"
            Toast.makeText(this, "Recording Started", Toast.LENGTH_SHORT).show()
            recordVideo(videoRecordingPath+"mmm_video.mp4")

        }
    }
}