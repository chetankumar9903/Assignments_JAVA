package com.aurionpro.payment;

import java.util.Scanner;

import com.aurionpro.order.Order;

public class CardPayment implements PaymentStrategy {
//    @Override
//    public void pay(Order order) {
//        System.out.println("Card payment processed successfully.");
//    }
	
	private static final Scanner scanner = new Scanner(System.in);

    @Override
    public void pay(Order order) {
        System.out.print("Enter 16-digit Debit Card Number: ");
        String cardNumber = scanner.nextLine().trim();
        while (!cardNumber.matches("\\d{16}")) {
            System.out.print("Invalid Card Number. Enter 16-digit Card Number: ");
            cardNumber = scanner.nextLine().trim();
        }

        System.out.print("Enter 4-digit PIN: ");
        String pin = scanner.nextLine().trim();
        while (!pin.matches("\\d{4}")) {
            System.out.print("Invalid PIN. Enter 4-digit PIN: ");
            pin = scanner.nextLine().trim();
        }

        System.out.println("Card payment successful!");
}
}
