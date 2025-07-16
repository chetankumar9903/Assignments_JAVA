package com.aurionpro.payment;

import java.util.Scanner;

import com.aurionpro.order.Order;

public class UpiPayment implements PaymentStrategy {
//    @Override
//    public void pay(Order order) {
//        System.out.println("Payment done via UPI.");
//    }
	
	private static final Scanner scanner = new Scanner(System.in);

    @Override
    public void pay(Order order) {
        System.out.print("Enter UPI ID (e.g., user@upi): ");
        String upiId = scanner.nextLine().trim();
        while (!upiId.matches("^[\\w.]+@\\w+$")) {
            System.out.print("Invalid UPI ID. Enter again: ");
            upiId = scanner.nextLine().trim();
        }

        System.out.print("Enter 4-digit UPI PIN: ");
        String pin = scanner.nextLine().trim();
        while (!pin.matches("\\d{4}")) {
            System.out.print("Invalid PIN. Enter 4-digit UPI PIN: ");
            pin = scanner.nextLine().trim();
        }

        System.out.println("UPI payment successful!");
	
}
}
