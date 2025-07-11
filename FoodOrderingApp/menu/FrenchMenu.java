package com.aurionpro.menu;

import java.util.ArrayList;
import java.util.List;

import com.aurionpro.food.CuisineType;
import com.aurionpro.food.Food;
import com.aurionpro.food.FoodType;

public class FrenchMenu implements MenuType {

	@Override
	public List<Food> getMenuItems() {
		List<Food> foodList = new ArrayList<>();
		foodList.add( new Food("Quiche", "Savory tart with eggs, cheese and veggies", 190,
                FoodType.MAIN_COURSE, true, 15, 4.2, true, CuisineType.FRENCH));

		foodList.add(new Food("Croissant", "Buttery flaky pastry", 100,
                FoodType.STARTER, true, 7, 4.5, true, CuisineType.FRENCH));

		foodList.add(new Food("Chocolate Souffle", "Light French chocolate dessert", 180,
                FoodType.DESSERT, true, 10, 4.7, true, CuisineType.FRENCH));

		return foodList;
	}

	@Override
	public String getMenuName() {
		// TODO Auto-generated method stub
		return "French Menu";
	}

	@Override
	public CuisineType getCuisineType() {
		// TODO Auto-generated method stub
		return CuisineType.FRENCH;
	}

}
