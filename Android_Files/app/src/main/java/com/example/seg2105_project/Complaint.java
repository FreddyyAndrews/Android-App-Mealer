package com.example.seg2105_project;

public class Complaint {

    private String complaintMessage;
    private Chef associatedChef;

    public Complaint(String complaintMessage, Chef associatedChef ){

        this.complaintMessage = complaintMessage;
        this.associatedChef = associatedChef;

    }

    public Complaint(){

    }

    public Chef getAssociatedChef(){
        return associatedChef;
    }

    public String getComplaintMessage(){
        return complaintMessage;
    }
}
