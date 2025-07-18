package com.aurionpro.assignment;

import java.util.Scanner;

public class GameRunner {

	public void start() {
		Scanner sc = new Scanner(System.in);
		TicTacToeGame game = new TicTacToeGame("Alice", "Bob");

		while (!game.isGameOver()) {
			game.printBoard();
			System.out.println(game.getCurrentPlayer().getName() + " (" + game.getCurrentPlayer().getMark()
					+ "), enter row and column (0-2):");

			int row, col;
			String result;

			while (true) {
				try {
					row = sc.nextInt();
					col = sc.nextInt();
					result = game.makeMove(row, col);

					if (result.toLowerCase().contains("wins") || result.toLowerCase().contains("draw")
							|| result.toLowerCase().contains("accepted")) {
						game.printBoard();
						break;

					} else {
						System.out.println(result);
						System.out.println("Please enter row and column again (0-2):");
					}
				} catch (Exception e) {
					System.out.println("Invalid input format. Enter numbers between 0-2.");
					sc.nextLine();
				}
			}

			System.out.println(result);
		}

//	        game.printBoard();
		System.out.println("Game ended. \nDo you want to play again? (yes/no):");
		if (sc.next().equalsIgnoreCase("yes")) {
            game.resetGame();
            start();  // recursive restart
        }
	}

}
