package com.aurionpro.menu;

import java.util.List;
import java.util.Scanner;

import com.aurionpro.food.CuisineType;
import com.aurionpro.food.Food;
import com.aurionpro.food.FoodType;
import com.aurionpro.model.AdminUIHelper;

public class ManageMenu {
	static Scanner scanner = new Scanner(System.in);
	public static void manageMenu() {
		
	    while (true) {
	        System.out.println("\n=========== MENU MANAGEMENT ===========");
	        System.out.println("1. View Menu Items");
	        System.out.println("2. Add New Item");
	        System.out.println("3. Update Existing Item");
	        System.out.println("4. Delete Item");
	        System.out.println("5. Back to Admin Panel");
	        System.out.print("Choose option: ");

	        String choice = scanner.nextLine().trim();
	        switch (choice) {
	            case "1":
	                AdminUIHelper.viewAllMenuItems();
	                break;
	            case "2":
	                addMenuItem();
	                break;
	            case "3":
	                updateMenuItem();
	                break;
	            case "4":
	                deleteMenuItem();
	                break;
	            case "5":
	                return;
	            default:
	                System.out.println("Invalid choice.");
	        }
	    }
	}
	private static void addMenuItem() {
	    try {
	        System.out.println("\n----- Add New Menu Item -----");

	        System.out.print("Enter item name: ");
	        String name = scanner.nextLine().trim();

	        System.out.print("Enter description: ");
	        String description = scanner.nextLine().trim();

	        System.out.print("Enter price: ");
	        double price = Double.parseDouble(scanner.nextLine());

	        System.out.print("Enter type (STARTER, MAIN_COURSE, DESSERT, BEVERAGE): ");
	        FoodType type = FoodType.valueOf(scanner.nextLine().trim().toUpperCase());

	        System.out.print("Is it available? (yes/no): ");
	        boolean availability = scanner.nextLine().trim().equalsIgnoreCase("yes");

	        System.out.print("Enter preparation time (in minutes): ");
	        int prepTime = Integer.parseInt(scanner.nextLine().trim());

	        System.out.print("Enter rating (0.0 to 5.0): ");
	        double rating = Double.parseDouble(scanner.nextLine().trim());

	        System.out.print("Is it vegetarian? (yes/no): ");
	        boolean isVeg = scanner.nextLine().trim().equalsIgnoreCase("yes");

	        System.out.print("Enter cuisine (INDIAN, ITALIAN, CHINESE, MEXICAN): ");
	        CuisineType cuisine = CuisineType.valueOf(scanner.nextLine().trim().toUpperCase());

	        Food newItem = new Food(name, description, price, type, availability, prepTime, rating, isVeg, cuisine);
	        MenuType menu = MenuFactory.getMenu(cuisine);
	        menu.getMenuItems().add(newItem);

	        System.out.println("Item added successfully.");

	    } catch (Exception e) {
//	    	throw new FieldNotFilledException("Field Not Filled Properly");
	        System.out.println("Error adding item: " + e.getMessage());
	    }
	}

	public static void updateMenuItem() {
		try {
			System.out.println("\n----- Update Menu Item -----");
		
	    System.out.print("Enter Cuisine to update from: ");
	    CuisineType cuisine = CuisineType.valueOf(scanner.nextLine().toUpperCase());
	    List<Food> items = MenuFactory.getMenu(cuisine).getMenuItems();

	    if (items.isEmpty()) {
	        System.out.println("No items found in this cuisine.");
	        return;
	    }

	    for (int i = 0; i < items.size(); i++) {
	        System.out.printf("%d. %s\n", i + 1, items.get(i));
	    }

	    System.out.print("Enter item number to update: ");
	    int index = Integer.parseInt(scanner.nextLine()) - 1;
	    if (index < 0 || index >= items.size()) {
	        System.out.println("Invalid selection.");
	        return;
	    }

	    Food old = items.get(index);

	    System.out.print("Enter new name [" + old.getName() + "]: ");
	    String name = scanner.nextLine().trim();
	    if (!name.isEmpty()) old.setName(name);

	    System.out.print("Enter new price [" + old.getPrice() + "]: ");
	    String priceStr = scanner.nextLine().trim();
	    if (!priceStr.isEmpty()) old.setPrice(Double.parseDouble(priceStr));

	    System.out.print("Change to vegetarian? (yes/no) [" + (old.isVeg() ? "yes" : "no") + "]: ");
	    String veg = scanner.nextLine().trim();
	    if (!veg.isEmpty()) old.setVeg(veg.equalsIgnoreCase("yes"));

	    System.out.println("Item updated successfully.");
	}catch(Exception e) {
//		throw new FieldNotFilledException("Field Not Filled Properly");
		 System.out.println("Error adding item: " + e.getMessage());
	}
	}
		

	public static void deleteMenuItem() {
		try {
			System.out.println("\n----- Delete Menu Item -----");
	
	    System.out.print("Enter Cuisine to delete from: ");
	    CuisineType cuisine = CuisineType.valueOf(scanner.nextLine().toUpperCase());
	    List<Food> items = MenuFactory.getMenu(cuisine).getMenuItems();

	    if (items.isEmpty()) {
	        System.out.println("No items found in this cuisine.");
	        return;
	    }

	    for (int i = 0; i < items.size(); i++) {
	        System.out.printf("%d. %s\n", i + 1, items.get(i));
	    }

	    System.out.print("Enter item number to delete: ");
	    int index = Integer.parseInt(scanner.nextLine()) - 1;
	    if (index < 0 || index >= items.size()) {
	        System.out.println("Invalid selection.");
	        return;
	    }

	    Food removed = items.remove(index);
	    System.out.println("Removed: " + removed.getName());
	}catch(Exception e) {
//		 System.out.println("");
		 System.out.println("Error adding item: " + e.getMessage());
	}
	}


}
