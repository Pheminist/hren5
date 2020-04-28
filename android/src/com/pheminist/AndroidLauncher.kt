package com.pheminist

import android.Manifest
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.hardware.camera2.CameraCharacteristics
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.camera.view.CameraView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.pheminist.controller.Controller

class AndroidLauncher : AndroidApplication(), LifecycleOwner {
    private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

    lateinit var layout: RelativeLayout

    private val CAMERA_PERMISSION = Manifest.permission.CAMERA
    private val RECORD_AUDIO_PERMISSION = Manifest.permission.RECORD_AUDIO
    private val READ_EXTERNAL_STORAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE
    private val WRITE_EXTERNAL_STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE

    private var RC_PERMISSION = 101

    private lateinit var camera: Camera
    private lateinit var cameraView: CameraView

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
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
        camera = Camera(this)
        cameraView = camera.cameraView
        val config = AndroidApplicationConfiguration()
        val gdxView = initializeForView(Controller(AndroidHorner(), camera), config)
//        val context = context
//        val inflater = LayoutInflater.from(context)
        layout = RelativeLayout(this)
        layout.addView(gdxView)

//		ConstraintLayout cameraView = (ConstraintLayout) inflater.inflate(R.layout.camera_view,null,false);
//		layout.addView(cameraView);
        cameraView.layoutParams = FrameLayout.LayoutParams(200, 100)
        cameraView.scaleType = CameraView.ScaleType.CENTER_INSIDE
        cameraView.captureMode = CameraView.CaptureMode.VIDEO
        cameraView.cameraLensFacing = CameraCharacteristics.LENS_FACING_FRONT

        setContentView(layout)
        layout.addView(cameraView)

        if (checkPermissions()) camera.startCameraSession() else requestPermissions()

    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(CAMERA_PERMISSION, RECORD_AUDIO_PERMISSION,
                READ_EXTERNAL_STORAGE_PERMISSION, WRITE_EXTERNAL_STORAGE_PERMISSION), RC_PERMISSION)
    }

    private fun checkPermissions(): Boolean {
        return ((ActivityCompat.checkSelfPermission(this, CAMERA_PERMISSION)) == PackageManager.PERMISSION_GRANTED
                && (ActivityCompat.checkSelfPermission(this, RECORD_AUDIO_PERMISSION)) == PackageManager.PERMISSION_GRANTED
                && (ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE_PERMISSION)) == PackageManager.PERMISSION_GRANTED
                && (ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE_PERMISSION)) == PackageManager.PERMISSION_GRANTED)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
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
                if (allPermissionsGranted) camera.startCameraSession() else permissionsNotGranted()
            }
        }
    }

//    private fun startCameraSession() {
//        cameraView.bindToLifecycle(this)
//    }

    private fun permissionsNotGranted() {
        AlertDialog.Builder(this).setTitle("Permissions required")
                .setMessage("These permissions are required to use this app. Please allow Camera, Audio, Read and Write SD permissions first")
                .setCancelable(false)
                .setPositiveButton("Grant") { dialog, which -> requestPermissions() }
                .show()
    }

    // todo костыль
    override fun onPause() {
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
        super.onPause()
        Log.d("HrenAndroid", "onPause  after super.onPause()")
    }

    override fun onResume() {
        super.onResume()
        lifecycleRegistry.currentState = Lifecycle.State.RESUMED

    }

    override fun onStop() {
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
        super.onStop()

        Log.d("HrenAndroid", "onStop")
    }

    override fun onDestroy() {
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        super.onDestroy()
        Log.d("HrenAndroid", "onDestroy")
    }
}