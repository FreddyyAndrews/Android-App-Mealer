package com.example.seg2105_project;

public class Chef extends User{
    private int suspensionLength; // If user is not suspended SuspensionLength is 0
    private boolean isBanned;
    private String description;
    // TODO: We'll need a VoidCheque data type
    private int numMealsSold;
    private double rating;
    private int numRatings;

    public Chef(String firstName, String lastName, String email, String address, String type, String description){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.type = type;
        this.suspensionLength = 0;
        this.isBanned = false;
        this.description = description;
        this.numMealsSold = 0;
        this.rating = 5.0;
        this.numRatings = 0;
    }

    // Getters for Chef-specific Attributes
    public int getSuspensionLength(){
        return this.suspensionLength;
    }
    public boolean getBanned(){
        return this.isBanned;
    }
    public String getDescription(){
        return this.description;
    }
    public int getNumMealsSold(){
        return this.numMealsSold;
    }
    public double getRating(){
        return this.rating;
    }

    // Setting Chef-specific Attributes
    public void setRating(double rating){
        this.numRatings++;
        // TODO: Implement a star rating calculator
    }
    public void setNumMealsSold(int numMeals){
        this.numMealsSold += numMeals;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setSuspensionLength(int suspensionLength){
        this.suspensionLength = suspensionLength;
    }
    public void setBanned(boolean banned){
        this.isBanned = banned;
    }
}
