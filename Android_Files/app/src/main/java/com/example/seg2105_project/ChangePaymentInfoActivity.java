package com.example.seg2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangePaymentInfoActivity extends AppCompatActivity {

    //Widgets
    Button btnBack;
    Button btnRegister;
    EditText inputCardNumber;
    EditText inputExpDate;
    EditText inputCvv;
    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference appDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        btnBack = findViewById(R.id.btnBack);
        btnRegister = findViewById(R.id.btnRegister);
        inputCardNumber = findViewById(R.id.inputCardNumber);
        inputExpDate = findViewById(R.id.inputExpDate);
        inputCvv = findViewById(R.id.inputCvv);
        //Firebase set-up
        appDatabaseReference = FirebaseDatabase.getInstance().getReference("people");
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( ChangePaymentInfoActivity.this, ClientActivity.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    try{
                        appDatabaseReference.child(currentUser.getUid()).child("card number").setValue(inputCardNumber.getText().toString());
                        appDatabaseReference.child(currentUser.getUid()).child("Expiry Date").setValue(inputExpDate.getText().toString());
                        appDatabaseReference.child(currentUser.getUid()).child("CVV").setValue(inputCvv.getText().toString());
                        Toast.makeText(ChangePaymentInfoActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent( ChangePaymentInfoActivity.this, ClientActivity.class));
                    }catch(Exception e){
                        Toast.makeText(ChangePaymentInfoActivity.this, "Failed to update", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
    //Validates user entries and sets the correct error to the text fields
    private boolean validate(){
        Boolean isValid = true;
        try{
            long creditCardNumber = Long.parseLong(inputCardNumber.getText().toString());
        }catch (Exception e){
            inputCardNumber.setError("Invalid Card Number");
            isValid= false;
        }
        try{
            int expDate = Integer.parseInt(inputExpDate.getText().toString());
        }catch (Exception e){
            inputExpDate.setError("Invalid Expiry Date");
            isValid= false;
        }
        try{
            int cvv = Integer.parseInt(inputCvv.getText().toString());
        }catch (Exception e){
            inputCvv.setError("Invalid CVV");
            isValid= false;
        }
        String creditCardNumber = inputCardNumber.getText().toString();
        String expDate = inputExpDate.getText().toString();
        String cvv = inputCvv.getText().toString();

        if(creditCardNumber.length() < 15 || creditCardNumber.length() > 16){
            inputCardNumber.setError("Invalid Card Number");
            isValid= false;
        }
        if(expDate.length() != 4 ){
            inputExpDate.setError("Invalid Expiry Date");
            isValid= false;
        }
        if(cvv.length() != 3){
            inputCvv.setError("Invalid CVV");
            isValid= false;
        }

        return  isValid;
    }
}