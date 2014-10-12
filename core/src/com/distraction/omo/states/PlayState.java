package com.distraction.omo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.distraction.omo.Omo;
import com.distraction.omo.states.TransitionState.Type;
import com.distraction.omo.ui.Glow;
import com.distraction.omo.ui.Score;
import com.distraction.omo.ui.TextImage;
import com.distraction.omo.ui.Tile;

public class PlayState extends State {
	
	private final int MAX_FINGERS = 2;
	
	public enum Difficulty {
		EASY,
		NORMAL,
		HARD,
		INSANE;
	}
	
	private int level;
	private int maxLevel;
	private Difficulty difficulty;
	private int[] args;
	
	private Score score;
	private float scoreTimer;
	
	private Tile[][] tiles;
	private int tileSize;
	private float boardOffset;
	private int boardHeight;
	
	private Array<Tile> selected;
	private Array<Tile> finished;
	
	private boolean showing;
	private float timer;
	
	// level stuff
	private TextureRegion light;
	private TextureRegion dark;
	
	// glow stuff
	private Array<Glow> glows;
	
	// wrong stuff
	private boolean done;
	private float wrongTimer;
	
	private TextImage back;
	
	public PlayState(GSM gsm, Difficulty difficulty) {
		
		super(gsm);
		
		level = 1;
		this.difficulty = difficulty;
		
		selected = new Array<Tile>();
		finished = new Array<Tile>();
		
		args = getArgs();
		createBoard(args[0], args[1]);
		createFinished(args[2]);
		
		score = new Score(Omo.WIDTH / 2, Omo.HEIGHT - 50);
		
		light = Omo.res.getAtlas("pack").findRegion("light");
		dark = Omo.res.getAtlas("pack").findRegion("dark");
		
		glows = new Array<Glow>();
		
		back = new TextImage("back", Omo.WIDTH / 2, 100);
		
	}
	
	private int[] getArgs() {
		int[] ret = new int[3];
		if(difficulty == Difficulty.EASY) {
			ret[0] = 3;
			ret[1] = 3;
			if(level >= 1 && level <= 3) {
				ret[2] = 3;
			}
			else if(level == 4 || level == 5) {
				ret[2] = 4;
			}
			maxLevel = 5;
		}
		if(difficulty == Difficulty.NORMAL) {
			ret[0] = 4;
			ret[1] = 4;
			if(level == 1 || level == 2) {
				ret[2] = 4;
			}
			if(level == 3 || level == 4) {
				ret[2] = 5;
			}
			if(level == 5 || level == 6) {
				ret[2] = 6;
			}
			maxLevel = 6;
		}
		if(difficulty == Difficulty.HARD) {
			ret[0] = 5;
			ret[1] = 5;
			if(level == 1 || level == 2) {
				ret[2] = 6;
			}
			if(level == 3 || level == 4) {
				ret[2] = 7;
			}
			if(level == 5 || level == 6) {
				ret[2] = 8;
			}
			if(level == 7 || level == 8) {
				ret[2] = 9;
			}
			maxLevel = 8;
		}
		if(difficulty == Difficulty.INSANE) {
			ret[0] = 6;
			ret[1] = 6;
			if(level == 1 || level == 2) {
				ret[2] = 8;
			}
			if(level == 3 || level == 4) {
				ret[2] = 9;
			}
			if(level == 5 || level == 6) {
				ret[2] = 10;
			}
			if(level == 7 || level == 8) {
				ret[2] = 11;
			}
			if(level == 9 || level == 10) {
				ret[2] = 12;
			}
			maxLevel = 10;
		}
		return ret;
	}
	
	private void createBoard(int numRows, int numCols) {
		tiles = new Tile[numRows][numCols];
		tileSize = Omo.WIDTH / tiles[0].length;
		boardHeight = tileSize * tiles.length;
		boardOffset = (Omo.HEIGHT - boardHeight) / 2;
		for(int row = 0; row < tiles.length; row++) {
			for(int col = 0; col < tiles[0].length; col++) {
				tiles[row][col] =
					new Tile(
						col * tileSize + tileSize / 2,
						row * tileSize + boardOffset + tileSize / 2,
						tileSize,
						tileSize);
				tiles[row][col].setTimer(
					(-(tiles.length - row) - col) * 0.1f);
			}
		}
	}
	
	public void createFinished(int numTilesToLight) {
		
		showing = true;
		
		timer = 0;
		scoreTimer = 5;
		wrongTimer = 0;
		
		selected.clear();
		finished.clear();
		
		for(int i = 0; i < numTilesToLight; i++) {
			int row = 0;
			int col = 0;
			do {
				row = MathUtils.random(tiles.length - 1);
				col = MathUtils.random(tiles[0].length - 1);
			} while(finished.contains(tiles[row][col], true));
			finished.add(tiles[row][col]);
			tiles[row][col].setSelected(true);
		}
		
	}
	
	public void checkShowing(float dt) {
		if(showing) {
			timer += dt;
			if(timer > 2) {
				if(timer % 0.15f < 0.07f) {
					for(int i = 0; i < finished.size; i++) {
						finished.get(i).setSelected(true);
					}
				}
				else {
					for(int i = 0; i < finished.size; i++) {
						finished.get(i).setSelected(false);
					}
				}
			}
			if(timer > 4) {
				showing = false;
				for(int i = 0; i < finished.size; i++) {
					finished.get(i).setSelected(false);
				}
			}
		}
	}
	
	public boolean isFinished() {
		for(int i = 0; i < finished.size; i++) {
			Tile tf = finished.get(i);
			if(!selected.contains(tf, true)) {
				return false;
			}
		}
		return true;
	}
	
	public void done() {
		gsm.set(new TransitionState(
			gsm,
			this,
			new ScoreState(gsm, score.getScore()),
			Type.EXPAND));
	}
	
	public void handleInput() {
		
		for(int i = 0; i < MAX_FINGERS; i++) {
			
			if(!showing && !done && Gdx.input.isTouched(i)) {
				
				mouse.x = Gdx.input.getX(i);
				mouse.y = Gdx.input.getY(i);
				cam.unproject(mouse);
				
				if(mouse.x > 0 &&
					mouse.x < Omo.WIDTH &&
					mouse.y > boardOffset &&
					mouse.y < boardOffset + boardHeight) { 
					int row = (int) ((mouse.y - boardOffset) / tileSize);
					int col = (int) (mouse.x / tileSize);
					if(!tiles[row][col].isSelected()) {
						tiles[row][col].setSelected(true);
						selected.add(tiles[row][col]);
						glows.add(new Glow(
							tiles[row][col].getx(),
							tiles[row][col].gety(),
							tileSize,
							tileSize));
						if(isFinished()) {
							done = true;
							level++;
							int inc = (int) (scoreTimer * 10);
							int dec = 5 * (selected.size - finished.size);
							for(int j = 0; j < selected.size; j++) {
								Tile tile = selected.get(j);
								if(!finished.contains(tile, true)) {
									tile.setWrong();
								}
							}
							if(dec == 0) {
								wrongTimer = 1;
							}
							score.incrementScore(inc - dec);
						}
					}
				}
				
			}
			
		}
		
		if(Gdx.input.justTouched()) {
			mouse.x = Gdx.input.getX();
			mouse.y = Gdx.input.getY();
			cam.unproject(mouse);
			if(back.contains(mouse.x, mouse.y)) {
				gsm.set(new TransitionState(
					gsm,
					this,
					new DifficultyState(gsm),
					Type.EXPAND));
			}
		}
		
	}
	
	public void update(float dt) {
		
		handleInput();
		
		checkShowing(dt);
		
		score.update(dt);
		
		if(done) {
			wrongTimer += dt;
			if(wrongTimer >= 1 && glows.size == 0 && score.isDone()) {
				args = getArgs();
				createBoard(args[0], args[1]);
				createFinished(args[2]);
				done = false;
				if(level > maxLevel) {
					done();
				}
			}
		}
		
		if(!showing) {
			scoreTimer -= dt;
		}
		
		for(int i = 0; i < glows.size; i++) {
			glows.get(i).update(dt);
			if(glows.get(i).shouldRemove()) {
				glows.removeIndex(i);
				i--;
			}
		}
		
		for(int row = 0; row < tiles.length; row++) {
			for(int col = 0; col < tiles[0].length; col++) {
				tiles[row][col].update(dt);
			}
		}
		
	}
	
	public void render(SpriteBatch sb) {
		
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		
		score.render(sb);
		back.render(sb);
		int totalWidth = 10 * (2 * maxLevel - 1);
		for(int i = 0; i < maxLevel; i++) {
			if(i < level) {
				sb.draw(
					light,
					(Omo.WIDTH - totalWidth) / 2 + i * 20,
					Omo.HEIGHT - 100,
					10, 10);
			}
			else {
				sb.draw(
					dark,
					(Omo.WIDTH - totalWidth) / 2 + i * 20,
					Omo.HEIGHT - 100,
					10, 10);
			}
		}
		for(int row = 0; row < tiles.length; row++) {
			for(int col = 0; col < tiles[0].length; col++) {
				tiles[row][col].render(sb);
			}
		}
		for(int i = 0; i < glows.size; i++) {
			glows.get(i).render(sb);
		}
		
		sb.end();
		
	}
	
}
