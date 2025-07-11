package com.aurionpro.menu;

import java.util.ArrayList;
import java.util.List;

import com.aurionpro.food.CuisineType;
import com.aurionpro.food.Food;
import com.aurionpro.food.FoodType;

public class AmericanMenu implements MenuType {

	@Override
	public List<Food> getMenuItems() {
		// TODO Auto-generated method stub
		List<Food> foodList = new ArrayList<>();
		foodList.add( new Food("Cheeseburger", "Beef patty with cheese in a bun", 220,
                FoodType.MAIN_COURSE, true, 10, 4.3, false, CuisineType.AMERICAN));

		foodList.add(new Food("Fries", "Crispy golden potato fries", 90,
                FoodType.STARTER, true, 5, 4.4, true, CuisineType.AMERICAN));

		foodList.add(new Food("Apple Pie", "Baked dessert with apple filling", 150,
                FoodType.DESSERT, true, 12, 4.5, true, CuisineType.AMERICAN));

		return foodList;
	}

	@Override
	public String getMenuName() {
		// TODO Auto-generated method stub
		return "American Menu";
	}

	@Override
	public CuisineType getCuisineType() {
		// TODO Auto-generated method stub
		return CuisineType.AMERICAN;
	}

}
