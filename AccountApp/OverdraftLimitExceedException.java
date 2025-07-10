package com.aurionpro.exceptions;

public class OverdraftLimitExceedException extends RuntimeException {
	boolean overdraftused = false;

	public OverdraftLimitExceedException(boolean overdraftused) {
		super();
		this.overdraftused = overdraftused;
	}
	
	public String getMessage() {
		return "Cannot process this payment, overdraft already used.";
	}

}
