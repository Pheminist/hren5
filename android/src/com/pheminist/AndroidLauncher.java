package com.pheminist;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.pheminist.controller.Controller;

public class AndroidLauncher extends AndroidApplication {
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		Context context = getContext();
		LayoutInflater inflater = LayoutInflater.from(context);
//		RelativeLayout blayout = (RelativeLayout) inflater.inflate(R.layout.custom_layout, null, false);
		Button blayout = (Button) inflater.inflate(R.layout.but_view, null, false);

//		LinearLayout linear = (LinearLayout)findViewById(R.id.myLayout);
		RelativeLayout layout = new RelativeLayout(this);
		View gdxView=initializeForView(new Controller(new AndroidHorner()), config);
		gdxView.setElevation(0);
		layout.addView(gdxView);
		layout.addView(blayout);



//		TextView tv = new TextView(this);
//		tv.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
//		tv.setText("Added tv");
//		layout.addView(tv);




		setContentView(layout);


//		initialize(new Controller(new AndroidHorner()), config);
	}
}
