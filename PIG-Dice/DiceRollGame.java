package com.aurionpro.piggame;
public class DiceRollGame {
	public static final int TARGET_SCORE = 20;
	public static final int MAX_TURNS = 5;

	public static void main(String[] args) {
		System.out.println("Reach 20 points in 5 turns or less.");
		System.out.println("If you roll a 1, ALL your points reset to 0!\n");

		Die die = new Die();
		Player player = new Player();
		UserInput userInput = new UserInput();

		for (int turnNumber = 1; turnNumber <= MAX_TURNS; turnNumber++) {
			System.out.println("\nTURN " + turnNumber);
			Turn turn = new Turn(die, userInput, player);
			boolean won = turn.playTurn();

			if (won) {
				System.out.println("\nYou WON");
				System.out.println("\nYou finished in " + turnNumber + " turns!");
				
				return;
			}
		}
		System.out.println("\nYou LOST");
		System.out.println("\nGame over! You didn't reach " + TARGET_SCORE + " points in " + MAX_TURNS + " turns.");
	}
}
