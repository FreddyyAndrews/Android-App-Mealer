package com.example.seg2105_project;
public class User extends Object {
    public String firstName, lastName, email, address, type;

    public User() {}

    public User (String firstName, String lastName, String email, String address, String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.type = type;
    }
}
