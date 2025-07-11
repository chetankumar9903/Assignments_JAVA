package com.aurionpro.menu;

import java.util.ArrayList;
import java.util.List;

import com.aurionpro.food.CuisineType;
import com.aurionpro.food.Food;
import com.aurionpro.food.FoodType;

public class IndianMenu implements MenuType {

	@Override
	public List<Food> getMenuItems() {
		// TODO Auto-generated method stub
		List<Food> foodList = new ArrayList<>();
		foodList.add(new Food("Paneer Tikka", "Grilled paneer cubes", 150,
                FoodType.STARTER, true, 15, 4.5, true, CuisineType.INDIAN));

        foodList.add(new Food("Butter Chicken", "Creamy tomato chicken curry", 250,
                FoodType.MAIN_COURSE, true, 20, 4.7, false, CuisineType.INDIAN));

        foodList.add(new Food("Gulab Jamun", "Sweet fried dumplings", 70,
                FoodType.DESSERT, true, 10, 4.6, true, CuisineType.INDIAN));

        foodList.add(new Food("Masala Chai", "Spiced Indian tea", 30,
                FoodType.BEVERAGE, true, 5, 4.2, true, CuisineType.INDIAN));
		
		return foodList;
	}

	@Override
	public String getMenuName() {
		
		return "Indian Menu";
	}

	@Override
	public CuisineType getCuisineType() {
		
		return CuisineType.INDIAN;
	}

}
