package com.aurionpro.payment;

import com.aurionpro.order.Order;

public class UpiPayment implements PaymentStrategy {
    @Override
    public void pay(Order order) {
        System.out.println("Payment done via UPI.");
    }
}
