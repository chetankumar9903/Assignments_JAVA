package com.aurionpro.piggame;

import java.util.Random;

public class Die {
	private Random random = new Random();


//	public Die(Random random) {
//		this.random = new Random();
//	}

	public int roll() {
		return random.nextInt(6) + 1;
	}
}
