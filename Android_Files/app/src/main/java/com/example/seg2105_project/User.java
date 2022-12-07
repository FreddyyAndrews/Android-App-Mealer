package com.example.seg2105_project;
public class User extends Object {

    //Instance Variables
    public String firstName, lastName, email, address, type;

    //Constructors
    public User() {}

    public User (String firstName, String lastName, String email, String address, String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.type = type;
    }

    //Getters and Setters
    public void setType(String type){
        this.type = type;
    }
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getEmail() {return email;}
    public String getAddress() {return address;}
    public String getType() {return type;}

}
