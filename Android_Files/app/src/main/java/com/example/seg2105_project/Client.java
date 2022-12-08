package com.example.seg2105_project;

public class Client extends User {
    private CreditCard creditCard;

    public Client(String firstName, String lastName, String email, String address, String type){
        super(firstName, lastName, email, address, type);
        this.creditCard = null;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
}
