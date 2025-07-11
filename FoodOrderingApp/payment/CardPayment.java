package com.aurionpro.payment;

import com.aurionpro.order.Order;

public class CardPayment implements PaymentStrategy {
    @Override
    public void pay(Order order) {
        System.out.println("Card payment processed successfully.");
    }
}
