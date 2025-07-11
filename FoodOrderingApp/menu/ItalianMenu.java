package com.aurionpro.menu;

import java.util.ArrayList;
import java.util.List;

import com.aurionpro.food.CuisineType;
import com.aurionpro.food.Food;
import com.aurionpro.food.FoodType;

public class ItalianMenu  implements MenuType {

	@Override
	public List<Food> getMenuItems() {
		List<Food> foodList = new ArrayList<>();

        foodList.add(new Food("Bruschetta", "Grilled bread with tomato topping", 120,
                FoodType.STARTER, true, 10, 4.3, true, CuisineType.ITALIAN));

        foodList.add(new Food("Margherita Pizza", "Classic Italian cheese pizza", 220,
                FoodType.MAIN_COURSE, true, 15, 4.5, true, CuisineType.ITALIAN));

        foodList.add(new Food("Tiramisu", "Coffee-flavored dessert", 150,
                FoodType.DESSERT, true, 10, 4.8, true, CuisineType.ITALIAN));

        foodList.add(new Food("Espresso", "Strong Italian coffee", 80,
                FoodType.BEVERAGE, true, 3, 4.4, true, CuisineType.ITALIAN));

        return foodList;
	}

	@Override
	public String getMenuName() {
		
		return "Italian Menu" ;
	}

	@Override
	public CuisineType getCuisineType() {
		
		return CuisineType.ITALIAN;
	}

}
