package com.example.seg2105_project;

public class CreditCard {
    private int cardNumber;
    private int expiryMonth;
    private int expiryYear;
    private int cvv;

    public CreditCard(int cardNumber, int expiryMonth, int expiryYear, int cvv){
        this.cardNumber = cardNumber;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cvv = cvv;
    }
}
