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

public class RegisterClient extends AppCompatActivity {
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
                startActivity(new Intent( RegisterClient.this, RegisterActivity.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dbIdEmail = currentUser.getEmail().replace('.', '~');
                if( validateCreditInfo(inputCardNumber.getText().toString(), inputExpDate.getText().toString(), inputCvv.getText().toString()) ){
                    try {
                        appDatabaseReference.child(dbIdEmail).child("card number").setValue(inputCardNumber.getText().toString());
                        appDatabaseReference.child(dbIdEmail).child("Expiry Date").setValue(inputExpDate.getText().toString());
                        appDatabaseReference.child(dbIdEmail).child("CVV").setValue(inputCvv.getText().toString());
                        Toast.makeText(RegisterClient.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent( RegisterClient.this, LoggedInActivity.class));
                    }catch(Exception e){
                        Toast.makeText(RegisterClient.this, "Failed to update", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
    //Validates user entries and sets the correct error to the text fields
    public boolean validateCreditInfo(String cardNb, String expDate, String cvv){
        Boolean isValid = true;

        if(cardNb.length() < 15){
            inputCardNumber.setError("Card number needs to be 15 digits in length");
            isValid= false;
        }
        if(expDate.length() < 4 ){
            inputExpDate.setError("Expiry date needs to be 4 digit in length");
            isValid= false;
        }
        if(cvv.length() < 3){
            inputCvv.setError("CVV needs to be 3 digits in length");
            isValid= false;
        }

        return  isValid;
    }
}