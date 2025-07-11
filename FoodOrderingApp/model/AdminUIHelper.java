package com.aurionpro.model;

import java.util.List;
import java.util.Scanner;

import com.aurionpro.delivery.DeliveryService;
import com.aurionpro.food.CuisineType;
import com.aurionpro.food.Customer;
import com.aurionpro.menu.ManageMenu;
import com.aurionpro.menu.MenuFactory;
import com.aurionpro.menu.MenuType;
import com.aurionpro.order.Order;
import com.aurionpro.order.OrderService;

import com.aurionpro.payment.PaymentDetails;


public class AdminUIHelper {
    private static final Scanner scanner = new Scanner(System.in);

    public static void adminPanel(OrderService orderService, DeliveryService deliveryService) {
        while (true) {
            System.out.println("\n===================== ADMIN PANEL =====================");
            System.out.println("1. View All Orders");
            System.out.println("2. View All Menu Items");
            System.out.println("3. View All Delivery Agents");
            System.out.println("4. View Available Delivery Agents");
            System.out.println("5. Set Delivery Fee");
            System.out.println("6. View Order by ID");
            System.out.println("7. View All Customers");
            System.out.println("8. Manage Menu");
            System.out.println("9. View All Payments");
            System.out.println("10. Exit Admin Panel");            
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    viewAllOrders(orderService);
                    break;
                case "2":
                    viewAllMenuItems();
                    break;
                case "3":
                	viewAllAgents(deliveryService);
                	break;
                case "4":
                	viewAvailableAgents(deliveryService);
                    break;
                case "5":
                    setDeliveryFee();
                    break;
                case "6":
                    viewOrderById(orderService);
                    break;
                case "7":
                    viewAllCustomers(orderService);
                    break;
                case "8":
                    ManageMenu.manageMenu();
                    break;
                case "9":
                    viewAllPayments(orderService);
                    break;
              case "10":
                    System.out.println("Exiting Admin Panel...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void viewAllOrders(OrderService orderService) {
        List<Order> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("No orders available.");
        } else {
            System.out.println("\nAll Orders:");
            for (Order order : orders) {
                System.out.println(order);
            }
        }
    }


    
    public static void viewAllMenuItems() {
        System.out.println("\nMenu Items:");
        for (CuisineType cuisine : CuisineType.values()) {
            MenuType menu = MenuFactory.getMenu(cuisine);
            System.out.println("\n-- " + cuisine.name() + " Menu --");
            menu.getMenuItems().forEach(System.out::println);
        }
    }

    private static void viewAvailableAgents(DeliveryService deliveryService) {
        System.out.println("\nAvailable Delivery Agents:");
        deliveryService.showAvailableAgents();
    }

    private static void setDeliveryFee() {
        System.out.print("Enter new delivery fee : ");
        try {
            double newFee = Double.parseDouble(scanner.nextLine());
            PaymentDetails.setDeliveryFee(newFee);
            System.out.println("Delivery fee updated to ₹" + newFee);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }
    
    private static void viewAllAgents(DeliveryService deliveryService) {
        System.out.println("\nAll Delivery Agents:");
        deliveryService.showAllAgents();
    }

    
    private static void viewOrderById(OrderService orderService) {
        System.out.print("Enter Order ID to view: ");
        String orderId = scanner.nextLine().trim();

        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            System.out.println("\n================= ORDER DETAILS =================");
            System.out.println(order);
        } else {
            System.out.println("No order found with ID: " + orderId);
        }
    }
    
    private static void viewAllCustomers(OrderService orderService) {
        List<Customer> customers = orderService.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        System.out.println("\n================= REGISTERED CUSTOMERS =================");
        System.out.printf("%-6s | %-20s | %-12s | %-25s | %-25s%n", 
                          "ID", "Name", "Phone", "Address", "Email");
        System.out.println("----------------------------------------------------------------------");

        for (Customer c : customers) {
            System.out.println(c);
        }
    }
    private static void viewAllPayments(OrderService orderService) {
        System.out.println("\n========= Payment Records =========");

        List<PaymentDetails> payments = orderService.getAllPayments();
        if (payments.isEmpty()) {
            System.out.println("No payments found.");
            return;
        }

        int count = 1;
        for (PaymentDetails pd : payments) {
            System.out.println("Payment #" + count++);
            System.out.println(pd);
            System.out.println("-----------------------------------");
        }
    }

    
}
