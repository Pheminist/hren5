package com.pheminist.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pheminist.controller.Controller;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=1000;//1366;
		config.height=500;
		new LwjglApplication(new Controller(new DesktopHorner(),new VideoController()), config);
	}
}
