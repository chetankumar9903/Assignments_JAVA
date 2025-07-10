package com.aurionpro.piggame;

public class Turn {
	private Die die;
	private UserInput userInput;
	private Player player;

	public Turn(Die die, UserInput userInput, Player player) {
		this.die = die;
		this.userInput = userInput;
		this.player = player;
	}

	public boolean playTurn() {
		int turnScore = 0;
		boolean turnOver = false;

		while (!turnOver) {
			char choice = userInput.getChoice();

			if (choice == 'r') {
				int roll = die.roll();
				System.out.println("Die: " + roll);

				if (roll == 1) {
					player.resetScore();
					System.out.println("Rolled a 1! You lost all your points. Total score is reset to 0.");
					turnOver = true;
				} else {
					turnScore += roll;
				}
			} else if (choice == 'h') {
				player.addScore(turnScore);
				System.out.println("Score for turn: " + turnScore);
				System.out.println("Total score: " + player.getScore());
				turnOver = true;
			}

			if (player.hasReachedTarget(DiceRollGame.TARGET_SCORE)) {
				return true; // Early win
			}
		}

		return false;
	}
}