package com.distraction.omo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.omo.handler.Content;
import com.distraction.omo.states.GSM;
import com.distraction.omo.states.MenuState;

public class Omo extends ApplicationAdapter {
	
	public static final String TITLE = "Omo";
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	
	public static Content res;
	
	private SpriteBatch sb;
	private GSM gsm;
	
	public void create () {
		
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		
		res = new Content();
		res.loadAtlas("pack.pack", "pack");
		
		sb = new SpriteBatch();
		gsm = new GSM();
		gsm.push(new MenuState(gsm));
		
	}
	
	public void render () {
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(sb);
		
	}
	
}









