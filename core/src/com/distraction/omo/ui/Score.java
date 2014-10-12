package com.distraction.omo.ui;

public class Score extends TextImage {
	
	private int score;
	private int destScore;
	
	private float speed = 100;
	
	public Score(float x, float y) {
		super("0", x, y);
	}
	
	public void incrementScore(int i) {
		destScore = score + i;
		if(destScore < 0) destScore = 0;
	}
	
	public void update(float dt) {
		if(score < destScore) {
			score += (int) (speed * dt);
			if(score > destScore) {
				score = destScore;
			}
		}
		else if(score < destScore) {
			score -= (int) (speed * dt);
			if(score < destScore) {
				score = destScore;
			}
		}
		setText(Integer.toString(score));
	}
	
	public int getScore() { return score; }
	public boolean isDone() { return score == destScore; }
	
}









