package com.aurionpro.model;

import com.aurionpro.exceptions.NegativeAmountException;

public  abstract class Account {
	private String accountNo;
    private String name;
    private double balance;

    public Account(String accountNo, String name) {
        this.accountNo = accountNo;
        this.name = name;
        this.balance = 0;
    }

    
   
    public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public void credit(double amount) {
		if(amount <=0) 
			throw new NegativeAmountException(amount);
		balance += amount;
	    System.out.println("Credited: " + amount);
			
	
        
    }
	
//	public abstract void  credit(double amount);

	public abstract void debit(double amount);

	public void display() {
        System.out.println("Account No: " + accountNo);
        System.out.println("Name: " + name);
        System.out.println("Balance: " + balance);
    }
}

