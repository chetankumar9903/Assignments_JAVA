package com.aurionpro.delivery;

import com.aurionpro.order.Order;

public interface DeliveryAgent {
	 String getAgentName();
	    void assignOrder(Order order);
	    void deliverOrder(Order order);
	    boolean isBusy();
	    String toString();
}
