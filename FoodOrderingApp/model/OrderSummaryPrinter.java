package com.aurionpro.model;

import com.aurionpro.order.Order;
import com.aurionpro.order.OrderService;
import com.aurionpro.order.OrderedItem;
import com.aurionpro.food.Customer;
import com.aurionpro.food.Food;
import com.aurionpro.payment.PaymentDetails;

public class OrderSummaryPrinter {

    public static void printOrderSummary(OrderService orderService, Order order) {
        System.out.println("CUSTOMER INFORMATION");
        System.out.println("-------------------------------------------------------------");
        Customer customer = order.getCustomer();
        System.out.printf("Customer ID : %s\n", customer.getCustomerId());
        System.out.printf("Name        : %s\n", customer.getName());
        System.out.printf("Phone       : %s\n", customer.getPhone());
        System.out.printf("Email       : %s\n", customer.getEmail());
        System.out.printf("Address     : %s\n", customer.getAddress());

        System.out.println("\nORDER INFORMATION");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("Order ID          : %s\n", order.getOrderId());
        System.out.printf("Status            : %s\n", order.getStatus());
        System.out.printf("Order Date        : %s\n", order.getFormattedOrderDate());
        System.out.printf("Order Time        : %s\n", order.getFormattedOrderTime());
        System.out.printf("Estimated Delivery: %s\n", order.getFormattedDeliveryTime());
        String instructions = order.getSpecialInstructions();
        System.out.printf("Instructions      : %s\n", 
            (instructions == null || instructions.isEmpty()) ? "None" : instructions);


        System.out.println("\nORDERED ITEMS");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-5s %-20s %-8s %-5s %-10s %-10s\n", "No.", "Item", "Price", "Qty", "Subtotal", "Type");
        int count = 1;
        for (OrderedItem item : order.getItems()) {
            Food food = item.getFood();
            int qty = item.getQuantity();
            double subtotal = food.getPrice() * qty;

            System.out.printf("%-5d %-20s ₹%-7.2f %-5d ₹%-9.2f %-10s\n",
                    count++, food.getName(), food.getPrice(), qty, subtotal, food.getType());
        }

        System.out.println("\nPAYMENT DETAILS");
        System.out.println("-------------------------------------------------------------");
        PaymentDetails payment = order.getPaymentDetails();
        if (payment != null) {
            System.out.println(payment);  
        } else {
            System.out.println("Payment not processed yet.");
        }

        if (order.getAssignedAgent() != null) {
            System.out.println("\nDELIVERY DETAILS");
            System.out.println("-------------------------------------------------------------");
            System.out.println("Assigned Agent: " + order.getAssignedAgent().getAgentName());
        }
    }
}
