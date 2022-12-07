package com.example.seg2105_project;

public class Complaint {

    private String complaintMessage;
    public String associatedChefEmail;

    public Complaint(String complaintMessage, String associatedChefEmail ){
        this.complaintMessage = complaintMessage;
        this.associatedChefEmail = associatedChefEmail;
    }

    public Complaint() {

    }

    public String getEmail(){
        return associatedChefEmail;
    }

    public String getComplaintMessage(){
        return complaintMessage;
    }
}
