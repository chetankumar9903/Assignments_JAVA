package com.aurionpro.model;

import com.aurionpro.exceptions.OverdraftLimitExceedException;

public class CurrentAccount extends Account {
    private final double OVERDRAFT_LIMIT = 5000;
    private boolean overdraftUsed = false;
    double overdraftAmt=0;

    public CurrentAccount(String accountNo, String name) {
        super(accountNo, name);
    }

    @Override
    public void debit(double amount) {
        double bal = getBalance();
        
        if(overdraftUsed) {
        	throw new OverdraftLimitExceedException(overdraftUsed);
        }
        else if (bal >= amount) {
            setBalance(bal - amount);
            System.out.println("Debited: " + amount);
        } else if (!overdraftUsed && (amount <= bal + OVERDRAFT_LIMIT)) {
             double overdraftAmount = amount - bal;
            setBalance(0);
            overdraftUsed = true;
            overdraftAmt = overdraftAmount;
            System.out.println("Debited using overdraft: " + amount + " (Overdraft amount used: " + overdraftAmount + ")");
        } 
    }

//    @Override
//    public void credit(double amount) {
//    	double balance = getBalance();
//		if(amount <0) {
//			System.out.println("Cannot perform this operation as you put negative amount");
//		}else if(overdraftUsed) {
//			double remainingOverDraft = OVERDRAFT_LIMIT-overdraftAmt;
//			balance += amount;
//			balance -= remainingOverDraft;
//		}
//		else {
//			balance += amount;
//	        System.out.println("Credited: " + amount);
//			
//		}
//        
//    }
    public void showDetails() {
        display();
        System.out.println("Overdraft Limit: " + OVERDRAFT_LIMIT);
        System.out.println("Overdraft Used: " + (overdraftUsed ? "Yes" : "No"));
    }
}
