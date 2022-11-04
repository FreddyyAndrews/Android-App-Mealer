package com.example.seg2105_project;

public class Chef extends User{
    public int suspensionLength; // If user is not suspended SuspensionLength is 0
    public boolean banned;

    public Chef(){
        this.suspensionLength = 0;
        this.banned = false;
    }
}
