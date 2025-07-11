package com.aurionpro.payment;
import com.aurionpro.order.Order;

public interface PaymentStrategy {
    void pay(Order order);
}
