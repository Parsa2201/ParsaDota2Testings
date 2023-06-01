package com.pathfinder;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.pathfinder.MyGdxGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		// set configurations
		config.setForegroundFPS(60); // fps
		config.setTitle("Path Finder");    // title
		config.setMaximized(true);   // maximize
		config.useVsync(true);       // vsync

		new Lwjgl3Application(new MyGdxGame(), config);
	}
}
