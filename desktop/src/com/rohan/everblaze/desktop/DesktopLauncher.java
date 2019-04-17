package com.rohan.everblaze.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import main.java.com.rohan.everblaze.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.vSyncEnabled = true;
		config.title = "EverBlaze";
		config.width = 1000;
		config.height = 800;
		config.addIcon("UI/icon.png", Files.FileType.Internal);
		new LwjglApplication(new Main(), config);
	}
}
