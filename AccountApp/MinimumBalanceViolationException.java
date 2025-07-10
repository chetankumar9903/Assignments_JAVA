package com.aurionpro.exceptions;

public class MinimumBalanceViolationException extends RuntimeException{
	double balance;

	public MinimumBalanceViolationException(double amount) {
		super();
		this.balance = amount;
	}
	public String getMessage() {
		return "Your minimum balance is violated, Current balance is : "+balance;
	}

}
