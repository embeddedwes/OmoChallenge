package com.distraction.omo.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.omo.Omo;

public class TextImage extends Box {
	
	private TextureRegion[][] fontSheet;
	private String text;
	private int size;
	
	public TextImage(String text, float x, float y) {
		
		this.text = text;
		this.x = x;
		this.y = y;
		
		size = 50;
		setText(text);
		
		TextureRegion sheet =
			Omo.res.getAtlas("pack").findRegion("fontsheet");		
		fontSheet = sheet.split(size, size);
		
	}
	
	public void setText(String text) {
		this.text = text;
		width = size * text.length();
		height = size;
	}
	
	public void render(SpriteBatch sb) {
		for(int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if(c >= 'a' && c <= 'z') {
				c -= 'a';
			}
			else if(c >= '0' && c <= '9') {
				c -= '0';
				c += 27;
			}
			int index = (int) c;
			int row = index / fontSheet[0].length;
			int col = index % fontSheet[0].length;
			sb.draw(
				fontSheet[row][col],
				x - width / 2 + 50 * i,
				y - height / 2
			);
		}
	}
	
}
