    package com.aurionpro.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.aurionpro.delivery.DeliveryService;
import com.aurionpro.food.CuisineType;
import com.aurionpro.food.Customer;
import com.aurionpro.food.Food;
import com.aurionpro.order.Order;
import com.aurionpro.order.OrderPaymentMode;
import com.aurionpro.order.OrderService;
import com.aurionpro.order.OrderStatus;
import com.aurionpro.order.OrderedItem;
import com.aurionpro.payment.PaymentService;

public class ConsoleUIHelper {
	private static final Scanner scanner = new Scanner(System.in);

	public static Order createOrder(OrderService orderService, Customer customer, List<Food> menuItems) {
	    Order order = orderService.createOrder(customer);

//	    System.out.println("\n Menu Items:");
//	    for (int i = 0; i < menuItems.size(); i++) {
//	        Food f = menuItems.get(i);
//	        System.out.printf("%d. %s - ₹%.2f [%s, %s]\n", i + 1, f.getName(), f.getPrice(), f.getType(), f.isVeg() ? "Veg" : "Non-Veg");
//	    }

	    
	    
//	    System.out.println("\nMenu Items by Cuisine:");
//	    System.out.println("\n====================== MENU ITEMS ==========================");
	    Map<CuisineType, List<Food>> cuisineMap = menuItems.stream()
	            .collect(Collectors.groupingBy(Food::getCuisine));

//	    int count = 1;
//	    for (CuisineType cuisine : cuisineMap.keySet()) {
//	        System.out.println("\n== " + cuisine.name() + " Cuisine ==");
//	        for (Food f : cuisineMap.get(cuisine)) {
//	            System.out.printf("%d. %s - ₹%.2f [%s, %s]\n",
//	                count++, f.getName(), f.getPrice(),
//	                f.getType(), f.isVeg() ? "Veg" : "Non-Veg");
//	        }
//	    }
	    
	    System.out.print("\nDo you want to see only vegetarian options? (yes/no): ");
	    String vegOnlyInput = scanner.nextLine().trim().toLowerCase();
	    boolean vegOnly = vegOnlyInput.equals("yes") || vegOnlyInput.equals("y");

	    int count = 1;
	    System.out.println("\n====================== MENU ITEMS ==========================");
	    for (CuisineType cuisine : cuisineMap.keySet()) {
	        System.out.println("\n== " + cuisine.name() + " Cuisine ==");
	        for (Food f : cuisineMap.get(cuisine)) {
	            if (vegOnly && !f.isVeg()) continue; 

	            System.out.printf("%d. %s - ₹%.2f [%s, %s]\n",
	                count++, f.getName(), f.getPrice(),
	                f.getType(), f.isVeg() ? "Veg" : "Non-Veg");
	        }
	    }

	    System.out.println("\nEnter item numbers with quantity (format: 1:2,3:1): ");
	    String input = scanner.nextLine();
	    String[] selections = input.split(",");

	    for (String entry : selections) {
	        try {
	            String[] parts = entry.trim().split(":");
	            int index = Integer.parseInt(parts[0].trim()) - 1;
	            int quantity = parts.length > 1 ? Integer.parseInt(parts[1].trim()) : 1;

	            if (index >= 0 && index < menuItems.size()) {
	                Food food = menuItems.get(index);
	                orderService.addItemToOrder(order, food, quantity);
	                System.out.println("Added: " + food.getName() + " x " + quantity);
	            } else {
	                System.out.println("Invalid item index: " + (index + 1));
	            }
	        } catch (Exception e) {
	            System.out.println("Invalid input format: " + entry);
	        }
	    }

	    System.out.print("Any special instructions? (Leave blank if none): ");
	    String instructions = scanner.nextLine().trim();
	    if (!instructions.isEmpty()) {
	        orderService.addSpecialInstructions(order, instructions);
	    }

	    System.out.print("Select payment method (CASH, UPI, CARD): ");
	    String mode = scanner.nextLine().trim().toUpperCase();
	    try {
	        PaymentService paymentService = new PaymentService();
	        paymentService.processPayment(order, OrderPaymentMode.valueOf(mode));
	    } catch (IllegalArgumentException e) {
	        System.out.println("Invalid payment mode. Defaulting to CASH.");
	        PaymentService paymentService = new PaymentService();
	        paymentService.processPayment(order, OrderPaymentMode.CASH);
	        orderService.markOrderAsPaid(order, OrderPaymentMode.CASH);
	    }
	    orderService.setOrderStatus(order, OrderStatus.PLACED);
	    orderService.setOrderStatus(order, OrderStatus.PREPARING);
	    
	 // Assign a delivery agent
	    DeliveryService deliveryService = new DeliveryService();
	    deliveryService.assignAgent(order);

	    //  simulate delivery
	    System.out.print("Simulate delivery now? (yes/no): ");
	    String choice = scanner.nextLine().trim().toLowerCase();
	    if (choice.equals("yes")) {
	        deliveryService.deliverOrder(order);
	        orderService.setOrderStatus(order, OrderStatus.DELIVERED);
	    }else {
	    	System.out.println("Order is now being prepared and will be delivered soon.");
	    }

	    return order;
	  
	}


    //single cuisine selection
	
//    public static CuisineType chooseCuisine() {
//        System.out.println("Available Cuisines:");
//        CuisineType[] cuisines = CuisineType.values();
//        for (int i = 0; i < cuisines.length; i++) {
//            System.out.printf("%d. %s\n", i + 1, cuisines[i].name());
//        }
//
//        System.out.print("Choose a cuisine (enter number): ");
//        try {
//            int choice = Integer.parseInt(scanner.nextLine().trim());
//            if (choice >= 1 && choice <= cuisines.length) {
//                return cuisines[choice - 1];
//            }
//        } catch (Exception e) {
//            System.out.println("Invalid input.");
//        }
//        System.out.println("Defaulting to INDIAN");
//        return CuisineType.INDIAN;
//    }
	
	public static List<CuisineType> chooseCuisines() {
		System.out.println("==================== CUISINE SELECTION ======================\n");
	    System.out.println("Available Cuisines:");
	    CuisineType[] cuisines = CuisineType.values();
	    for (int i = 0; i < cuisines.length; i++) {
	        System.out.printf("%d. %s\n", i + 1, cuisines[i].name());
	    }

	    System.out.print("Choose cuisines (comma-separated numbers, e.g., 1,3): ");
	    String input = scanner.nextLine().trim();
	    List<CuisineType> selectedCuisines = new ArrayList<>();

	    try {
	        String[] parts = input.split(",");
	        for (String part : parts) {
	            int choice = Integer.parseInt(part.trim());
	            if (choice >= 1 && choice <= cuisines.length) {
	                selectedCuisines.add(cuisines[choice - 1]);
	            } else {
	                System.out.println("Invalid choice: " + part);
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("Invalid input. Defaulting to INDIAN only.");
	        selectedCuisines.add(CuisineType.INDIAN);
	    }

	    if (selectedCuisines.isEmpty()) {
	        selectedCuisines.add(CuisineType.INDIAN); 
	    }

	    return selectedCuisines;
	}


    
    
    public static void displayOrder(OrderService orderService, Order order) {
        System.out.println("\n=====================  FINAL ORDER SUMMARY =====================");

        System.out.println("CUSTOMER DETAILS:");
        System.out.printf("%-6s | %-20s | %-12s | %-25s | %-25s%n", "ID", "Name", "Phone", "Address", "Email");
        System.out.println("----------------------------------------------------------------------");
        System.out.println(order.getCustomer());

        System.out.println("\n ORDER DETAILS:");
        System.out.println("Order ID       : " + order.getOrderId());
        System.out.println("Status         : " + order.getStatus());
        System.out.println("Date           : " + order.getFormattedOrderDate());
        System.out.println("Order Time     : " + order.getFormattedOrderTime());
        System.out.println("Delivery Time  : " + order.getFormattedDeliveryTime());
        System.out.println("Instructions   : " + order.getSpecialInstructions());

        System.out.println("\n ORDERED ITEMS:");
        System.out.printf("%-10s %-20s %-8s %-5s %-10s %-10s %-10s %-10s\n",
                "ID", "Name", "Price", "Qty", "Subtotal", "Type", "Cuisine", "Veg?");
        for (OrderedItem item : order.getItems()) {
            Food food = item.getFood();
            int qty = item.getQuantity();
            double subtotal = food.getPrice() * qty;

            System.out.printf("%-10s %-20s ₹%-7.2f %-5d ₹%-9.2f %-10s %-10s %-10s\n",
                    food.getId(), food.getName(), food.getPrice(), qty, subtotal,
                    food.getType(), food.getCuisine(), food.isVeg() ? "Yes" : "No");
        }

        System.out.println("\n PAYMENT DETAILS:");
        if (order.getPaymentDetails() != null) {
            System.out.println(order.getPaymentDetails());
        } else {
            System.out.println(" Not paid yet.");
        }

        System.out.println("\n===================================================================");
    }




}
