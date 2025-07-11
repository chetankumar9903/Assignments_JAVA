package com.aurionpro.menu;

import com.aurionpro.exceptions.MenuNotAvailableException;
import com.aurionpro.food.CuisineType;

public class MenuFactory {
	public static MenuType getMenu(CuisineType cuisine) {
        switch (cuisine) {
            case INDIAN:
                return new IndianMenu();
            case ITALIAN:
                return new ItalianMenu();
            case CHINESE:
                 return new ChineseMenu(); 
            case MEXICAN:
            	return new MexicanMenu();
            case CONTINENTAL:
            	return new ContinentalMenu();
            case JAPANESE:
            	return new JapaneseMenu();
            case THAI:
            	return new ThaiMenu();
            case AMERICAN:
            	return new AmericanMenu();
            case FRENCH:
            	return new FrenchMenu();
            default:
//            	throw new MenuNotAvailableException("Menu for " + cuisine + " is not available yet.");
            	throw new IllegalArgumentException("Invalid Cuisine Type");
        }
    }	

}

