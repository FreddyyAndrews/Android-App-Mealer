package com.example.seg2105_project;

import java.util.ArrayList;

public class Admin extends User {
    //Instance Variables
    ArrayList<Complaint> inbox= new ArrayList<Complaint>();
    //Constructor

    public Admin() {}

    public Admin(String firstName, String lastName, String email, String address, String type, ArrayList<Complaint> inbox ){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.type = type;
        this.inbox = inbox;
    }
    //Getters and Setters
    public ArrayList<Complaint> getInbox(){return this.inbox;}
    //Admin Methods
    public void suspendChef(Chef suspendedChef, int lengthOfSuspension){
        //TODO Implement
    }

    public void dismissComplaint(Complaint dismissable){
        //TODO Implement
    }

    public void banChef(Chef BannedChef){
        //TODO Implement
    }
}