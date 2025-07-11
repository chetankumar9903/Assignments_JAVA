package com.aurionpro.menu;

import java.util.ArrayList;
import java.util.List;

import com.aurionpro.food.CuisineType;
import com.aurionpro.food.Food;
import com.aurionpro.food.FoodType;

public class ThaiMenu implements MenuType{

	@Override
	public List<Food> getMenuItems() {
		List<Food> foodList = new ArrayList<>();
		foodList.add(new Food("Tom Yum Soup", "Spicy Thai shrimp soup", 140,
                FoodType.STARTER, true, 8, 4.4, false, CuisineType.THAI));

		foodList.add(new Food("Pad Thai", "Stir-fried noodles with tamarind sauce", 200,
                FoodType.MAIN_COURSE, true, 12, 4.5, true, CuisineType.THAI));

		foodList.add(new Food("Sticky Rice with Mango", "Coconut sticky rice & mango", 160,
                FoodType.DESSERT, true, 10, 4.6, true, CuisineType.THAI));

		return foodList;
	}

	@Override
	public String getMenuName() {
		// TODO Auto-generated method stub
		return "Thai Menu";
	}

	@Override
	public CuisineType getCuisineType() {
		// TODO Auto-generated method stub
		return CuisineType.THAI	;
	}

}
