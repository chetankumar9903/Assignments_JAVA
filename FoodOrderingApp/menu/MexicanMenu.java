package com.aurionpro.menu;

import java.util.ArrayList;
import java.util.List;

import com.aurionpro.food.CuisineType;
import com.aurionpro.food.Food;
import com.aurionpro.food.FoodType;

public class MexicanMenu implements MenuType {

    @Override
    public List<Food> getMenuItems() {
        List<Food> foodList = new ArrayList<>();

        foodList.add(new Food("Nachos", "Crispy chips with cheese & salsa", 140,
                FoodType.STARTER, true, 7, 4.3, true, CuisineType.MEXICAN));

        foodList.add(new Food("Tacos", "Soft shell tacos with fillings", 180,
                FoodType.MAIN_COURSE, true, 10, 4.5, false, CuisineType.MEXICAN));

        foodList.add(new Food("Churros", "Fried dough dessert with sugar", 100,
                FoodType.DESSERT, true, 6, 4.6, true, CuisineType.MEXICAN));

        foodList.add(new Food("Horchata", "Sweet rice milk beverage", 70,
                FoodType.BEVERAGE, true, 5, 4.0, true, CuisineType.MEXICAN));

        return foodList;
    }

    @Override
    public String getMenuName() {
        return "Mexican Menu";
    }

    @Override
    public CuisineType getCuisineType() {
        return CuisineType.MEXICAN;
    }
}
