package com.aurionpro.order;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.aurionpro.food.Customer;
import com.aurionpro.food.Food;
import com.aurionpro.payment.PaymentDetails;

public class OrderService {

//    public Order createOrder(Customer customer, List<Food> menuItems) {
//        Scanner scanner = new Scanner(System.in);
//        Order order = new Order(customer);
//
//        System.out.println("\nSelect items by number (comma separated), e.g., 1,3,4:");
//        for (int i = 0; i < menuItems.size(); i++) {
//            Food f = menuItems.get(i);
//            System.out.printf("%d. %s - ₹%.2f [%s]\n", i + 1, f.getName(), f.getPrice(), f.getType());
//        }
//
//        String input = scanner.nextLine();
//        String[] selections = input.split(",");
//
//        for (String indexStr : selections) {
//            try {
//                int index = Integer.parseInt(indexStr.trim()) - 1;
//                if (index >= 0 && index < menuItems.size()) {
//                    order.addItem(menuItems.get(index));
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid number: " + indexStr);
//            }
//        }
//
//        System.out.println("\nItems added successfully to order.");
//        return order;
//    }
//
//    public void printOrderSummary(Order order) {
//        System.out.println(order);
//        for (Food f : order.getItems()) {
//            System.out.printf(" - %s (₹%.2f)\n", f.getName(), f.getPrice());
//        }
//    }
	
//	public Order createOrder(Customer customer) {
//        return new Order(customer);
//    }
	
	
	private List<Order> allOrders = new ArrayList<>();
	private List<Customer> customers = new ArrayList<>();
	
	public void registerCustomer(Customer customer) {
	    customers.add(customer);
	}

	public List<Customer> getAllCustomers() {
	    return customers;
	}

	public Order createOrder(Customer customer) {
	    Order order = new Order(customer);
	    allOrders.add(order);  
	    return order;
	}

	public List<Order> getAllOrders() {
	    return allOrders;
	}


//    public void addItemToOrder(Order order, Food food) {
//        if (!food.isAvailability()) {
//            System.out.println("Item " + food.getName() + " is not available.");
//            return;
//        }
//        order.addItem(food);
//        System.out.println("Added: " + food.getName());
//    }
	
	public void addItemToOrder(Order order, Food food, int quantity) {
	    order.addItem(food, quantity);

	}

    public void calculateEstimatedDeliveryTime(Order order) {
        int totalPrepTime = order.getItems().stream()
//                .mapToInt(item -> item.getFood().getPreparationTime() * item.getQuantity())
        		.mapToInt(item -> item.getFood().getPreparationTime())
                .sum();
        int buffer = 20; // delivery buffer time 
        LocalDateTime eta = order.getOrderTime().plusMinutes(totalPrepTime + buffer);
        order.setEstimatedDeliveryTime(eta);
    }
	
    public Order getOrderById(String orderId) {
        for (Order order : allOrders) {
            if (order.getOrderId().equalsIgnoreCase(orderId)) {
                return order;
            }
        }
        return null;
    }

	

    public void markOrderAsPaid(Order order, OrderPaymentMode paymentMode) {
        order.markAsPaid();  
//        System.out.println(" Payment successful via " + paymentMode);
    }

    public void setOrderStatus(Order order, OrderStatus status) {
        order.setStatus(status);
        System.out.println("Order status updated to: " + status);
    }

    public void addSpecialInstructions(Order order, String instructions) {
        order.setSpecialInstructions(instructions);
    }

    public void printOrderSummary(Order order) {
        System.out.println("========== ORDER SUMMARY ==========");
        System.out.println(order);
    }

    public void printItemDetails(Order order) {
        System.out.println("========== ITEM DETAILS ==========");
        System.out.printf("%-10s %-20s %-10s %-8s %-12s\n", "ID", "Name", "Price", "Type", "PrepTime");

        for (OrderedItem orderedItem : order.getItems()) {
            Food item = orderedItem.getFood();
            System.out.printf("%-10s %-20s ₹%-9.2f %-8s %-12d\n",
                    item.getId(), item.getName(), item.getPrice(), item.getType(), item.getPreparationTime());
        }
    }
    
    public List<PaymentDetails> getAllPayments() {
        List<PaymentDetails> payments = new ArrayList<>();
        for (Order order : allOrders) {
            if (order.getPaymentDetails() != null) {
                payments.add(order.getPaymentDetails());
            }
        }
        return payments;
    }


}

