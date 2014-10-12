package com.distraction.omo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.distraction.omo.Omo;
import com.distraction.omo.states.PlayState.Difficulty;
import com.distraction.omo.states.TransitionState.Type;
import com.distraction.omo.ui.TextImage;

public class DifficultyState extends State {
	
	private Array<TextImage> buttons;
	private TextImage back;
	
	public DifficultyState(GSM gsm) {
		
		super(gsm);
		
		String[] texts = { "easy", "normal", "hard", "insane" };
		
		buttons = new Array<TextImage>();
		for(int i = 0; i < texts.length; i++) {
			buttons.add(
				new TextImage(
					texts[i],
					Omo.WIDTH / 2,
					Omo.HEIGHT / 2 + 170 - 70 * i));
		}
		
		back = new TextImage("back", Omo.WIDTH / 2, 100);
		
	}
	
	public void handleInput() {
		if(Gdx.input.justTouched()) {
			mouse.x = Gdx.input.getX();
			mouse.y = Gdx.input.getY();
			cam.unproject(mouse);
			for(int i = 0; i < buttons.size; i++) {
				if(buttons.get(i).contains(mouse.x, mouse.y)) {
					gsm.set(new TransitionState(
						gsm,
						this,
						new PlayState(gsm, Difficulty.values()[i]),
						Type.EXPAND));
				}
			}
			if(back.contains(mouse.x, mouse.y)) {
				gsm.set(new TransitionState(
					gsm,
					this,
					new MenuState(gsm),
					Type.BLACK_FADE));
			}
		}
	}
	
	public void update(float dt) {
		handleInput();
	}
	
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		for(int i = 0; i < buttons.size; i++) {
			buttons.get(i).render(sb);
		}
		back.render(sb);
		sb.end();
	}
	
}









