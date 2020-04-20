package com.pheminist;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.camera.view.CameraView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.pheminist.controller.Controller;

public class AndroidLauncher extends AndroidApplication implements LifecycleOwner {
    private LifecycleRegistry lifecycleRegistry;

    public AndroidLauncher() {
        lifecycleRegistry = new LifecycleRegistry(this);
        lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
    }

	@NonNull
	public Lifecycle getLifecycle() {
		return lifecycleRegistry;
	}

	@Override
	public void onStart() {
		super.onStart();
		lifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        View gdxView = initializeForView(new Controller(new AndroidHorner()), config);

        Context context = getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        RelativeLayout layout = new RelativeLayout(this);
        layout.addView(gdxView);
//		CameraView cameraView = new CameraView(this);
//		cameraView.setLayoutParams(new FrameLayout.LayoutParams(100,100));

//		ConstraintLayout cameraView = (ConstraintLayout) inflater.inflate(R.layout.camera_view,null,false);
//		layout.addView(cameraView);

        CameraView cameraView = new CameraView(this);
        cameraView.setLayoutParams(new FrameLayout.LayoutParams(200, 100));
        cameraView.setScaleType(CameraView.ScaleType.CENTER_INSIDE);
        cameraView.setCaptureMode(CameraView.CaptureMode.VIDEO);
        cameraView.setCameraLensFacing(CameraCharacteristics.LENS_FACING_FRONT);
        layout.addView(cameraView);
        cameraView.bindToLifecycle(this);

//        Button blayout = (Button) inflater.inflate(R.layout.but_view, null, false);
//        layout.addView(blayout);
        setContentView(layout);
    }
}
