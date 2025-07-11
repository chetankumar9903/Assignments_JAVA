package com.aurionpro.menu;

import java.util.ArrayList;
import java.util.List;

import com.aurionpro.food.CuisineType;
import com.aurionpro.food.Food;
import com.aurionpro.food.FoodType;

public class JapaneseMenu implements MenuType {

	@Override
	public List<Food> getMenuItems() {
		// TODO Auto-generated method stub
		List<Food> foodList = new ArrayList<>();
		foodList.add(new Food("Sushi", "Rice rolls with fish or vegetables", 250, FoodType.MAIN_COURSE, true, 15, 4.6,
				false, CuisineType.JAPANESE));

		foodList.add(new Food("Miso Soup", "Soybean paste soup with tofu and seaweed", 120, FoodType.STARTER, true, 5,
				4.3, true, CuisineType.JAPANESE));

		foodList.add(new Food("Mochi", "Sweet rice cake with red bean filling", 130, FoodType.DESSERT, true, 7, 4.5,
				true, CuisineType.JAPANESE));

		return foodList;
	}

	@Override
	public String getMenuName() {
		return "Japanese Menu";
	}

	@Override
	public CuisineType getCuisineType() {
		return CuisineType.JAPANESE;
	}

}
