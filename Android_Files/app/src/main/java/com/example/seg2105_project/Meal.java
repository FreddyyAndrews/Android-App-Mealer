package com.example.seg2105_project;

public class Meal {
    private String mealName;
    private double price;
    private String mealType; // e.g. main dish, soup, desert, etc..
    private String cuisineType; // e.g. Italian, Chinese, Greek, etc..
    private String[] listOfIngredients;
    private String[] allergens;
    private String description;
    private boolean currentlyOffered;

    public Meal(String mealName, double price, String mealType, String cuisineType, String[] listOfIngredients,
                String[] allergens, String description, boolean currentlyOffered){
        this.mealName = mealName;
        this.price = price;
        this.mealType = mealType;
        this.cuisineType = cuisineType;
        this.listOfIngredients = listOfIngredients;
        this.allergens = allergens;
        this.description = description;
        this.currentlyOffered = currentlyOffered;
    }
}
