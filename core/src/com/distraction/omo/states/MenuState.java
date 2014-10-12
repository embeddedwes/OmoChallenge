package com.distraction.omo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.omo.Omo;
import com.distraction.omo.states.TransitionState.Type;
import com.distraction.omo.ui.Graphic;
import com.distraction.omo.ui.TextImage;

public class MenuState extends State {
	
	private Graphic title;
	private TextImage play, time;
	
	public MenuState(GSM gsm) {
		
		super(gsm);
		
		title = new Graphic(
				Omo.res.getAtlas("pack").findRegion("omo"),
				Omo.WIDTH / 2,
				Omo.HEIGHT / 2 + 100);
		
		play = new TextImage(
				"play",
				Omo.WIDTH / 2,
				Omo.HEIGHT / 2 - 50);

        time = new TextImage(
                "time",
                Omo.WIDTH / 2,
                Omo.HEIGHT / 2 - 120);
	}
	
	public void handleInput() {
		if(Gdx.input.justTouched()) {
			mouse.x = Gdx.input.getX();
			mouse.y = Gdx.input.getY();
			cam.unproject(mouse);
			if(play.contains(mouse.x, mouse.y)) {
				gsm.set(new TransitionState(
					gsm,
					this,
					new DifficultyState(gsm),
					Type.BLACK_FADE));
			}
            if(time.contains(mouse.x, mouse.y)) {
                gsm.set(new TransitionState(
                        gsm,
                        this,
                        new TimeState(gsm),
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
		title.render(sb);
		play.render(sb);
        time.render(sb);
		sb.end();
	}
	
}








