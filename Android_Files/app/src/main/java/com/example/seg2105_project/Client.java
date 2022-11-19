package com.example.seg2105_project;

public class Client extends User {
    private CreditCard creditCard;

    public Client(String firstName, String lastName, String email, String address, String type, String id){
        super(firstName, lastName, email, address, type, id);
        this.creditCard = null;
    }

    // TODO: We'll need a page for users to input their credit card info after the initial registration form.
    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
}
