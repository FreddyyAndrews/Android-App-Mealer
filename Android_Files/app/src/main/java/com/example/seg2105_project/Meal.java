package com.example.seg2105_project;

public class Meal {
    public String mealName;
    public String price;
    public String mealType; // e.g. main dish, soup, desert, etc..
    public String cuisineType; // e.g. Italian, Chinese, Greek, etc..
    public String listOfIngredients;
    public String allergens;
    public String description;
    public boolean currentlyOffered;
    public String associatedChefEmail;

    public Meal(String mealName, String price, String mealType, String cuisineType, String listOfIngredients,
                String allergens, String description, boolean currentlyOffered, String associatedChef){
        this.mealName = mealName;
        this.price = price;
        this.mealType = mealType;
        this.cuisineType = cuisineType;
        this.listOfIngredients = listOfIngredients;
        this.allergens = allergens;
        this.description = description;
        this.currentlyOffered = currentlyOffered;
        this.associatedChefEmail = associatedChef;
    }

    public void setCurrentlyOfferedState(boolean state ) {
        this.currentlyOffered = state;
    }
    public String getMealName(){return mealName;}
    public String getAssociatedChefEmail(){return associatedChefEmail;}
    public boolean getCurrentlyOffered(){return currentlyOffered;}

}
