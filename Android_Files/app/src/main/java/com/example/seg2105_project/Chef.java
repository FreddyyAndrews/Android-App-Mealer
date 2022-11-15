package com.example.seg2105_project;

public class Chef extends User {
    public double suspensionLength; // If user is not suspended SuspensionLength is 0
    public boolean isBanned;

    public Chef(){
        this.suspensionLength = 0;
        this.isBanned = false;
    }

    public Chef(String fName, String lName, String email, String address, String type) {
        super(fName, lName, email, address, type);
    }

    public void setSuspensionLength(double nbDays) {
        this.suspensionLength = nbDays;
    }

    public void setBanned(boolean status) {
        this.isBanned = status;
    }
}
