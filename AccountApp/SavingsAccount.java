package com.aurionpro.model;

import com.aurionpro.exceptions.MinimumBalanceViolationException;

public class SavingsAccount extends Account {
	private  double MIN_BALANCE = 1000;

    public SavingsAccount(String accountNo, String name) {
        super(accountNo, name);
    }
    
    
    @Override
    public void debit(double amount) {
        double bal = getBalance();
        double remainingBalance = bal - amount;
        if(MIN_BALANCE > remainingBalance)
        	throw new MinimumBalanceViolationException(remainingBalance);
        setBalance(bal - amount);
        System.out.println("Debited: " + amount);
       
        
//        if ((bal - amount) >= MIN_BALANCE) {
//            setBalance(bal - amount);
//            System.out.println("Debited: " + amount);
//        } else {
//            System.out.println("Cannot debit. Minimum balance of " + MIN_BALANCE + " must be maintained.");
//        }
    }

//    public void credit(double amount) {
//    	double balance = getBalance();
//		if(amount <0) {
//			System.out.println("Cannot perform this operation as you put negative amount");
//		}else {
//			balance += amount;
//	        System.out.println("Credited: " + amount);
//			
//		}
//        
//    }
    public void showDetails() {
        display();
        System.out.println("Minimum Balance Required: " + MIN_BALANCE);
    }
}


