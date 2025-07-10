package com.aurionpro.exceptions;

public class NegativeAmountException extends RuntimeException {
	double amount;

	public NegativeAmountException(double amount) {
		super();
		this.amount = amount;
	}
	
	public String getMessage() {
		return "Cannot credit/ debit the amount : "+amount+" as it is negative amount or zero.";
	}

}
