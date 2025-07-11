package com.aurionpro.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.aurionpro.delivery.DeliveryService;
import com.aurionpro.exceptions.FieldNotFilledException;
import com.aurionpro.exceptions.InValidFormatException;
import com.aurionpro.food.CuisineType;
import com.aurionpro.food.Customer;
import com.aurionpro.food.Food;
import com.aurionpro.menu.MenuFactory;
import com.aurionpro.menu.MenuType;
import com.aurionpro.model.AdminUIHelper;
import com.aurionpro.model.ConsoleUIHelper;
import com.aurionpro.model.OrderSummaryPrinter;
import com.aurionpro.order.Order;
import com.aurionpro.order.OrderService;
import com.aurionpro.receipt.TextReceiptGenerator;


public class FoodOrderingApp {

	public static void main(String[] args) {
	    Scanner scanner = new Scanner(System.in);
	    OrderService orderService = new OrderService();
	    DeliveryService deliveryService = new DeliveryService();

	    while (true) {
	        System.out.println("\n=============================================================");
	        System.out.println("                 Welcome to Food Ordering App               ");
	        System.out.println("=============================================================");
	        System.out.println("1. Place a New Order");
	        System.out.println("2. Access Admin Panel");
	        System.out.println("3. Exit");
	        System.out.print("Enter your choice: ");
	        String choice = scanner.nextLine().trim();

	        switch (choice) {
	            case "1":
	                customerFlow(scanner, orderService);  
	                break;

	            case "2":
	                System.out.print("Enter admin password: ");
	                String password = scanner.nextLine().trim();
	                if (password.equals("admin123")) {
	                	AdminUIHelper.adminPanel(orderService, deliveryService);
	                } else {
	                    System.out.println("Incorrect password.");
	                }
	                break;

	            case "3":
	                System.out.println("Thank you for using the Food Ordering App!");
	                System.exit(0);
	                break;

	            default:
	                System.out.println("Invalid choice. Please try again.");
	        }
	    }
	}
	private static void customerFlow(Scanner scanner, OrderService orderService) {
	    //customer details
	    Customer customer = null;
	    while (customer == null) {
	        try {
	            System.out.println("----- Enter Customer Details -----");
	            System.out.print("Name    : ");
	            String name = scanner.nextLine().trim();

	            System.out.print("Phone   : ");
	            String phone = scanner.nextLine().trim();

	            System.out.print("Address : ");
	            String address = scanner.nextLine().trim();

	            System.out.print("Email   : ");
	            String email = scanner.nextLine().trim();

	            customer = new Customer(name, phone, address, email);
	            System.out.println("\nCustomer registered successfully!\n");
	            orderService.registerCustomer(customer);

	        } catch (FieldNotFilledException | InValidFormatException e) {
	            System.out.println("Error: " + e.getMessage());
	            System.out.println("Please re-enter the customer details.\n");
	        }
	    }

	    // Choose Cuisines
	    List<CuisineType> selectedCuisines = ConsoleUIHelper.chooseCuisines();

	    // Load Menu
	    List<Food> menuItems = new ArrayList<>();
	    for (CuisineType cuisine : selectedCuisines) {
	        MenuType menu = MenuFactory.getMenu(cuisine);
	        menuItems.addAll(menu.getMenuItems());
	    }

	    // Create Order
	    Order order = ConsoleUIHelper.createOrder(orderService, customer, menuItems);

	    // Show Summary
	    System.out.println("\n=============================================================");
	    System.out.println("                    Final Order Summary                      ");
	    System.out.println("=============================================================\n");
	    OrderSummaryPrinter.printOrderSummary(orderService, order);

	    // if receipt needed
	    System.out.print("\nWould you like to print the receipt? (yes/no): ");
	    String response = scanner.nextLine().trim().toLowerCase();

	    if (response.equals("yes") || response.equals("y")) {
	        System.out.println("\nGenerating Receipt...");
	        new TextReceiptGenerator().generateReceipt(order);
	        System.out.println("Receipt saved successfully.");
	    } else {
	        System.out.println("Receipt skipped.");
	    }

	    System.out.println("\nThank you for using the Food Ordering App!");
	    System.out.println("=============================================================");
	}

}
