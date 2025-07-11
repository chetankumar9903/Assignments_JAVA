package com.aurionpro.order;

import com.aurionpro.food.Food;

public class OrderedItem {
    private Food food;
    private int quantity;

    public OrderedItem(Food food, int quantity) {
        this.food = food;
        this.quantity = quantity;
    }

    public Food getFood() {
        return food;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }

    public double getTotalPrice() {
        return food.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return String.format("%-10s %-20s ₹%-9.2f x %-2d = ₹%.2f [%s, %s]",
                food.getId(), food.getName(), food.getPrice(), quantity, getTotalPrice(),
                food.getType(), food.isVeg() ? "Veg" : "Non-Veg");
    }
}
