package com.aurionpro.test;

import java.util.Scanner;

import com.aurionpro.exceptions.MinimumBalanceViolationException;
import com.aurionpro.exceptions.NegativeAmountException;
import com.aurionpro.exceptions.OverdraftLimitExceedException;
import com.aurionpro.model.Account;
import com.aurionpro.model.CurrentAccount;
import com.aurionpro.model.SavingsAccount;

public class AccountTest {
	public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Account account = null;
        CurrentAccount curr = null;
        SavingsAccount sav = null;

        System.out.println("1. Open Current Account\n2. Open Savings Account");
        System.out.print("Enter choice: ");
        int ch = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Account No: ");
        String acc = sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        if (ch == 1) {
            curr = new CurrentAccount(acc, name);
            account = curr;
            System.out.println("Current Account Created.");
        } else if (ch == 2) {
            sav = new SavingsAccount(acc, name);
            account = sav;
            System.out.println("Savings Account Created.");
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        while (true) {
            System.out.println("\n1. Credit\n2. Debit\n3. Show Details\n4. Exit");
            System.out.print("Enter option: ");
            int option = sc.nextInt();

            switch (option) {
                case 1:
                	try {
                		System.out.print("Enter amount to credit: ");
                        account.credit(sc.nextDouble());
                	}catch(NegativeAmountException e) {
                		System.out.println(e.getMessage());
                	}
                	
//                	System.out.print("Enter amount to credit: ");
//                    account.credit(sc.nextDouble());
                    break;
                case 2:
                	try {
                    System.out.print("Enter amount to debit: ");
                    double amt = sc.nextDouble();
                    
                    	if (curr != null) curr.debit(amt);
                        else if (sav != null) sav.debit(amt);
                    }
                    catch(MinimumBalanceViolationException e) {
                    	System.out.println(e.getMessage());
                    }
                    catch(OverdraftLimitExceedException e) {
                    	System.out.println(e.getMessage());
                    }
                    catch(NegativeAmountException e) {
                		System.out.println(e.getMessage());
                	}
                    
//                    if (curr != null) curr.debit(amt);
//                    else if (sav != null) sav.debit(amt);
                    break;
                case 3:
                    if (curr != null) curr.showDetails();
                    else if (sav != null) sav.showDetails();
                    break;
                case 4:
                    System.out.println("Thank you!");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

}
