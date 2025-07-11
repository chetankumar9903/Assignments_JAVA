package com.aurionpro.delivery;

import com.aurionpro.order.Order;
import com.aurionpro.order.OrderStatus;

public class SwiggyAgent implements DeliveryAgent {
    private String name;
//    private boolean busy;
    private Order assignedOrder;
    public SwiggyAgent() {
        this.name = "SwiggyAgent";
    }

    public SwiggyAgent(String name) {
        this.name = name;
    }
    
    @Override
    public String getAgentName() {
        return name;
    }


    @Override
    public void assignOrder(Order order) {
//        busy = true;
        this.assignedOrder = order;
        order.setAssignedAgent(this);
        System.out.println("Order " + order.getOrderId() + " assigned to " + name);
        
    }

    @Override
    public void deliverOrder(Order order) {
        
//        busy = false;
//    	System.out.println(name + " delivered order " + order.getOrderId());
//        order.setStatus(OrderStatus.DELIVERED);
//        this.assignedOrder = null;  
    	 if (order.getStatus() != OrderStatus.DELIVERED) {
             order.setStatus(OrderStatus.DELIVERED);
             System.out.println(name + " delivered order " + order.getOrderId());
             this.assignedOrder = null; 
         }
    }

//    @Override
//    public boolean isBusy() {
//        return busy;
//    }

//    @Override
//    public String toString() {
//        return name + " (Available)";
//    }
    
//    public boolean isBusy() {
//        return assignedOrder != null && orderNotDelivered();
//    }
//    
//    private boolean orderNotDelivered() {
//        return assignedOrder.getStatus() != OrderStatus.DELIVERED;
//    }

    
    public boolean isBusy() {
        return assignedOrder != null && assignedOrder.getStatus() != OrderStatus.DELIVERED;
    }
    
    @Override
    public String toString() {
        return name ;
    }
	
}
