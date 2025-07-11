package com.aurionpro.menu;

import java.util.ArrayList;
import java.util.List;

import com.aurionpro.food.CuisineType;
import com.aurionpro.food.Food;
import com.aurionpro.food.FoodType;

public class ContinentalMenu  implements MenuType{

	@Override
	public List<Food> getMenuItems() {
		List<Food> foodList = new ArrayList<>();
		foodList.add(new Food("Grilled Chicken", "Chicken breast with herbs and butter", 300,
                FoodType.MAIN_COURSE, true, 20, 4.4, false, CuisineType.CONTINENTAL));
		
		foodList.add(new Food("Caesar Salad", "Romaine lettuce with parmesan and croutons", 180,
                FoodType.STARTER, true, 10, 4.3, true, CuisineType.CONTINENTAL));
		foodList.add(new Food("Creme Brulee", "French-style caramel dessert", 160,
                FoodType.DESSERT, true, 15, 4.7, true, CuisineType.CONTINENTAL));
		
		return foodList;
	}


	@Override
    public String getMenuName() {
        return "Continental Menu";
    }

    @Override
    public CuisineType getCuisineType() {
        return CuisineType.CONTINENTAL;
    }

}
