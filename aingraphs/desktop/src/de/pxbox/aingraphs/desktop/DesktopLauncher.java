package de.pxbox.aingraphs.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;

import de.pxbox.aingraphs.AIGraphs;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 600;
		config.width = 800;
		config.foregroundFPS = 30;
//		config.backgroundFPS = -1;
		config.initialBackgroundColor = new Color(0x222222);
		config.samples = 8;
		config.vSyncEnabled = true;
		config.title = "Ai Graphs";
		config.resizable = true;
		new LwjglApplication(new AIGraphs(), config);
	}
}
