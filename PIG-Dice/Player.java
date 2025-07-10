package com.aurionpro.piggame;

public class Player {
	private int totalScore;
	
//
//	public Player(int totalScore) {
//		this.totalScore = 0;
//	}

	public void addScore(int score) {
		totalScore += score;
	}

	public void resetScore() {
		totalScore = 0;
	}

	public int getScore() {
		return totalScore;
	}

	public boolean hasReachedTarget(int target) {
		return totalScore >= target;
	}
}
