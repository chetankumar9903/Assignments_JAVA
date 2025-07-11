package com.aurionpro.payment;

import java.util.UUID;

import com.aurionpro.order.Order;
import com.aurionpro.order.OrderPaymentMode;

public class PaymentService {

	public void processPayment(Order order, OrderPaymentMode mode) {
	    PaymentStrategy strategy;

	    switch (mode) {
	        case UPI:
	            strategy = new UpiPayment(); break;
	        case CARD:
	            strategy = new CardPayment(); break;
	        case CASH:
	        default:
	            strategy = new CashPayment(); break;
	    }

	    strategy.pay(order);

	    
	    double originalAmount = order.getTotalAmount();
	    double discount = originalAmount > 500 ? originalAmount * 0.10 : 0;
	    double deliveryFee = PaymentDetails.getDeliveryFee();
	    double finalAmount = originalAmount - discount + deliveryFee;

	    String referenceId = "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
	    PaymentDetails details = new PaymentDetails(mode, originalAmount, discount,finalAmount, referenceId,deliveryFee);

	    order.setPaymentDetails(details);
	    order.markAsPaid();
	}

}
