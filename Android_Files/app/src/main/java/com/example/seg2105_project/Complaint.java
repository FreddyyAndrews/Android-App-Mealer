package com.example.seg2105_project;

public class Complaint {

    private String complaintMessage;
    private User associatedChef;

    public Complaint(String complaintMessage, User associatedChef ){

        this.complaintMessage = complaintMessage;
        this.associatedChef = associatedChef;

    }

    public User getAssociatedChef(){
        return associatedChef;
    }

    public String getComplaintMessage(){
        return complaintMessage;
    }
}
