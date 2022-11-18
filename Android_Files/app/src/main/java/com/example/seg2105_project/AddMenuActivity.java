package com.example.seg2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddMenuActivity extends AppCompatActivity {
    //Widgets and firebase
    Button btnDiscard;
    Button btnSubmit;
    EditText inputMealName, inputMealType, inputCuisineType, inputListOfIngredients , inputPrice;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference appDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);
        btnDiscard = findViewById(R.id.btnDiscard);
        btnSubmit = findViewById(R.id.btnSubmit);
        inputCuisineType = findViewById(R.id.inputCuisineType);
        inputMealName = findViewById(R.id.inputMealName);
        inputMealType = findViewById(R.id.inputMealType);
        inputListOfIngredients = findViewById(R.id.inputListOfIngredients);
        inputPrice = findViewById(R.id.inputPrice);

        //Firebase Set-Up
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        appDatabaseReference = FirebaseDatabase.getInstance().getReference("people");

        btnDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( AddMenuActivity.this, EditMenuActivity.class));
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    try{
                        //TODO add meal to db
                        
                    }catch(Exception e){
                        //TODO give error message
                    }
                }
            }
        });
    }
    private boolean validate(){
        boolean returnVal = true;
        String price = inputPrice.getText().toString();
        String cuisineType = inputCuisineType.getText().toString();
        String mealName = inputMealName.getText().toString();
        String listOfIngredients = inputListOfIngredients.getText().toString();
        String mealType = inputMealType.getText().toString();

        try{
            double dPrice = Double.parseDouble(price);
        }catch(Exception e){
            inputPrice.setError("Input Valid Price");
            returnVal = false;
        }
        if(cuisineType.equals("")){
            inputCuisineType.setError("Field is Mandatory");
            returnVal = false;
        }
        if(mealName.equals("")){
            inputMealName.setError("Field is Mandatory");
            returnVal = false;
        }
        if(listOfIngredients.equals("")){
            inputListOfIngredients.setError("Field is Mandatory");
            returnVal = false;
        }
        if(mealType.equals("")){
            inputCuisineType.setError("Field is Mandatory");
            returnVal = false;
        }
        if(price.equals("")){
            inputPrice.setError("Field is Mandatory");
            returnVal = false;
        }

        return returnVal;
    }
}