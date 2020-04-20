package com.pheminist

import android.annotation.TargetApi
import android.hardware.camera2.CameraCharacteristics
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.camera.view.CameraView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.pheminist.controller.Controller

class AndroidLauncher : AndroidApplication(), LifecycleOwner {
    private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)
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
        val gdxView = initializeForView(Controller(AndroidHorner()), config)
        val context = context
        val inflater = LayoutInflater.from(context)
        val layout = RelativeLayout(this)
        layout.addView(gdxView)
        //		CameraView cameraView = new CameraView(this);
//		cameraView.setLayoutParams(new FrameLayout.LayoutParams(100,100));

//		ConstraintLayout cameraView = (ConstraintLayout) inflater.inflate(R.layout.camera_view,null,false);
//		layout.addView(cameraView);
        val cameraView = CameraView(this)
        cameraView.layoutParams = FrameLayout.LayoutParams(200, 100)
        cameraView.scaleType = CameraView.ScaleType.CENTER_INSIDE
        cameraView.captureMode = CameraView.CaptureMode.VIDEO
        cameraView.cameraLensFacing = CameraCharacteristics.LENS_FACING_FRONT
        layout.addView(cameraView)
        cameraView.bindToLifecycle(this)

        setContentView(layout)
    }

    init {
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
    }
}