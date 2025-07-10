package com.aurionpro.piggame;

import java.util.Scanner;

public class UserInput {
	private Scanner scanner = new Scanner(System.in);

	public char getChoice() {
		System.out.print("Roll or hold? (r/h): ");
		String input = scanner.nextLine().trim().toLowerCase();
		if (input.length() == 0) return ' ';
		char choice = input.charAt(0);
		if (choice != 'r' && choice != 'h') {
			System.out.println("Invalid input. Please enter 'r' or 'h'.");
			return getChoice(); // Retry
		}
		return choice;
	}
}

