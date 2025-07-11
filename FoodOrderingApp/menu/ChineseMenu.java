package com.aurionpro.menu;

import java.util.ArrayList;
import java.util.List;

import com.aurionpro.food.CuisineType;
import com.aurionpro.food.Food;
import com.aurionpro.food.FoodType;

public class ChineseMenu implements MenuType {

	@Override
	public List<Food> getMenuItems() {
		List<Food> foodList = new ArrayList<>();

        foodList.add(new Food("Spring Rolls", "Crispy veggie rolls", 130,
                FoodType.STARTER, true, 8, 4.2, true, CuisineType.CHINESE));

        foodList.add(new Food("Chow Mein", "Stir-fried noodles with veggies", 180,
                FoodType.MAIN_COURSE, true, 12, 4.4, true, CuisineType.CHINESE));

        foodList.add(new Food("Sweet and Sour Chicken", "Tangy chicken in sauce", 220,
                FoodType.MAIN_COURSE, true, 15, 4.5, false, CuisineType.CHINESE));

        foodList.add(new Food("Lemon Ice Tea", "Chilled tea with lemon", 60,
                FoodType.BEVERAGE, true, 5, 4.1, true, CuisineType.CHINESE));

        return foodList;
	}

	@Override
	public String getMenuName() {
		// TODO Auto-generated method stub
		return "Chinese Menu";
	}

	@Override
	public CuisineType getCuisineType() {
		// TODO Auto-generated method stub
		return CuisineType.CHINESE;
	}

}
