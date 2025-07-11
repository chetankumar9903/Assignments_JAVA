package com.aurionpro.menu;

import java.util.List;

import com.aurionpro.food.CuisineType;
import com.aurionpro.food.Food;

public interface MenuType {
	List<Food> getMenuItems(); 
	String getMenuName();           
    CuisineType getCuisineType();   

}
