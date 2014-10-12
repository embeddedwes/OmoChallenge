package com.distraction.omo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.distraction.omo.Omo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Omo.WIDTH / 2;
		config.height = Omo.HEIGHT / 2;
		config.title = Omo.TITLE;
		config.x = 220;
		config.y = 90;
		
		new LwjglApplication(new Omo(), config);
		
	}
}
