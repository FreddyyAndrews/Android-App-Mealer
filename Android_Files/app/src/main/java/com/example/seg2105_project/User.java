package com.example.seg2105_project;
public class User {
    public String firstName, lastName, email, city, address, province, country, type;

    public User() {}

    public User (String firstName, String lastName, String email, String address, String city, String province, String country, String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.city = city;
        this.province = province;
        this.country = country;
        this.type = type;
    }
}
