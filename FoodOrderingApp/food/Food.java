package com.aurionpro.food;

public class Food {
	private String id;
	private String name;
	private String description;
	private double price;
	private FoodType type;
	private boolean availability;
	private int preparationTime;
	private double rating; 
	private boolean isVeg;
	private CuisineType cuisine;
	private static int counter = 1;

	
	public Food() {
		super();
	}

	public Food(String name, String description, double price, FoodType type, boolean availability, int preparationTime,
			double rating, boolean isVeg, CuisineType cuisine) {
		this.id = String.format("F%03d", counter++);
		this.name = name;
		this.description = description;
		this.price = price;
		this.type = type;
		this.availability = availability;
		this.preparationTime = preparationTime;
		this.rating = rating;
		this.isVeg = isVeg;
		this.cuisine = cuisine;
	}

	public String getId() {
		return id;
	}
//
//	public void setId(String id) {
//		this.id = id;
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public FoodType getType() {
		return type;
	}

	public void setType(FoodType type) {
		this.type = type;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public int getPreparationTime() {
		return preparationTime;
	}

	public void setPreparationTime(int preparationTime) {
		this.preparationTime = preparationTime;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public boolean isVeg() {
		return isVeg;
	}

	public void setVeg(boolean isVeg) {
		this.isVeg = isVeg;
	}
	
	public CuisineType getCuisine() {
	    return cuisine;
	}

	public void setCuisine(CuisineType cuisine) {
	    this.cuisine = cuisine;
	}

//	@Override
//	public String toString() {
//		return String.format("[%s] %s (%s, %s) - ₹%.2f\nDescription: %s\nVeg: %s | Rating: %.1f | Prep: %d min",
//			    id, name, type, cuisine, price, description, isVeg ? "Yes" : "No", rating, preparationTime);
//
//	}
	
	@Override
	public String toString() {
	    return String.format("%-10s %-20s %-15s %-12s ₹%-7.2f %-5s %s",
	        id, name, type, cuisine, price, (isVeg ? "Yes" : "No"), description);
	}

	
	
	public void updateRating(double newRating) {
		this.rating = (this.rating + newRating) / 2; 
	}

	public void markUnavailable() {
		this.availability = false;
	}

}
