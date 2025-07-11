package com.aurionpro.order;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.aurionpro.delivery.DeliveryAgent;
import com.aurionpro.food.Customer;
import com.aurionpro.food.Food;
import com.aurionpro.payment.PaymentDetails;

public class Order {
    private static int counter = 1;

    private String orderId;
    private Customer customer;
    private double totalAmount;
    private LocalDateTime orderTime;
    private boolean isPaid;

    private OrderStatus status;
    private OrderPaymentMode paymentMode;
    private String specialInstructions;
    private LocalDateTime estimatedDeliveryTime;
    private DeliveryAgent assignedAgent;
    private PaymentDetails paymentDetails;
    private List<OrderedItem> items;

    public Order(Customer customer) {
        this.orderId = String.format("O%03d", counter++);
        this.customer = customer;
        this.items = new ArrayList<>();
        this.orderTime = LocalDateTime.now();
        this.status = OrderStatus.PLACED;
        this.isPaid = false;
    }

    public void addItem(Food food, int quantity) {
        for (OrderedItem item : items) {
            if (item.getFood().equals(food)) {
                item.increaseQuantity(quantity);
                totalAmount += food.getPrice() * quantity;
                return;
            }
        }
        items.add(new OrderedItem(food, quantity));
        totalAmount += food.getPrice() * quantity;
    }

    public void markAsPaid() {
        this.isPaid = true;
    }

    public String getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderedItem> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public OrderPaymentMode getPaymentMode() {
        return paymentMode;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }

    public LocalDateTime getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(LocalDateTime estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public DeliveryAgent getAssignedAgent() {
        return assignedAgent;
    }

    public void setAssignedAgent(DeliveryAgent assignedAgent) {
        this.assignedAgent = assignedAgent;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void calculateEstimatedDeliveryTime() {
        int totalPrepTime = items.stream()
                                 .mapToInt(item -> item.getFood().getPreparationTime())
                                 .sum();
        int deliveryBuffer = 20;
        this.estimatedDeliveryTime = orderTime.plusMinutes(totalPrepTime + deliveryBuffer);
    }

    public String getFormattedOrderDate() {
        return orderTime.toLocalDate().toString();
    }

    public String getFormattedOrderTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return orderTime.toLocalTime().format(formatter);
    }

    public String getFormattedDeliveryTime() {
        calculateEstimatedDeliveryTime();
        if (estimatedDeliveryTime == null) return "-";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return estimatedDeliveryTime.toLocalTime().format(formatter);
    }

    @Override
    public String toString() {
        return String.format("""
                Order ID: %s
                Customer: %s
                Order Time: %s
                Items: %d
                Total: ₹%.2f
                Paid: %s %s
                Status: %s
                Instructions: %s
                Delivery Time: %s
                Delivery Agent: %s
                """,
                orderId,
                customer.getName(),
                getFormattedOrderTime(),
                items.size(),
                totalAmount,
                isPaid ? "Yes" : "No",
                (paymentMode != null ? "via " + paymentMode : ""),
                status,
                specialInstructions != null ? specialInstructions : "-",
                getFormattedDeliveryTime(),
                assignedAgent != null ? assignedAgent.getAgentName() : "-"
        );
    }
    
 
    public String getSummary() {
        return String.format("OrderID: %s | Customer: %s | Status: %s | Total: ₹%.2f",
            orderId,
            customer.getName(),
            status,
            paymentDetails != null ? paymentDetails.getFinalAmount() : 0.0);
    }

}
