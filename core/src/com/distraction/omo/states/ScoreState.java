package com.distraction.omo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.omo.Omo;
import com.distraction.omo.states.TransitionState.Type;
import com.distraction.omo.ui.TextImage;

public class ScoreState extends State {
	
	private TextImage image;
	
	public ScoreState(GSM gsm, int score) {
		super(gsm);
		image = new TextImage(
				Integer.toString(score),
				Omo.WIDTH / 2,
				Omo.HEIGHT / 2);
	}
	
	public void handleInput() {
		if(Gdx.input.justTouched()) {
			gsm.set(new TransitionState(
				gsm,
				this,
				new MenuState(gsm),
				Type.EXPAND));
		}
	}
	
	public void update(float dt) {
		handleInput();
	}
	
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		image.render(sb);
		sb.end();
	}
	
}








