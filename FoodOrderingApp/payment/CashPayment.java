package com.aurionpro.payment;

import com.aurionpro.order.Order;

public class CashPayment implements PaymentStrategy {
    @Override
    public void pay(Order order) {
        System.out.println("Paid in cash at delivery.");
    }
}
