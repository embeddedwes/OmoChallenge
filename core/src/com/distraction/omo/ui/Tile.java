package com.distraction.omo.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.omo.Omo;

public class Tile extends Box {
	
	protected TextureRegion light;
	protected TextureRegion dark;
	
	private boolean selected;
	
	protected float totalWidth;
	protected float totalHeight;
	protected float timer;
	protected float maxTime = 0.5f;
	
	private boolean wrong;
	
	public Tile(float x, float y, float width, float height) {
		
		this.x = x;
		this.y = y;
		this.totalWidth = width - 8;
		this.totalHeight = height - 8;
		
		light = Omo.res.getAtlas("pack").findRegion("light");
		dark = Omo.res.getAtlas("pack").findRegion("dark");
		
	}
	
	public void setMaxTime(float t) {
		maxTime = t;
	}
	
	public void setTimer(float t) {
		timer = t;
	}
	
	public void setSelected(boolean b) {
		selected = b;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setWrong() {
		wrong = true;
	}
	
	public void update(float dt) {
		if(width < totalWidth && height < totalHeight) {
			timer += dt;
			width = (timer / maxTime) * totalWidth;
			height = (timer / maxTime) * totalHeight;
			if(width < 0) width = 0;
			if(height < 0) height = 0;
			if(width > totalWidth) width = totalWidth;
			if(height > totalHeight) height = totalHeight;
		}
	}
	
	public void render(SpriteBatch sb) {
		if(selected) {
			if(wrong) {
				sb.setColor(1, 0, 0, 1);
			}
			sb.draw(light, x - width / 2, y - height / 2, width, height);
			sb.setColor(1, 1, 1, 1);
		}
		else {
			sb.draw(dark, x - width / 2,y - height / 2, width, height);
		}
	}
	
}












