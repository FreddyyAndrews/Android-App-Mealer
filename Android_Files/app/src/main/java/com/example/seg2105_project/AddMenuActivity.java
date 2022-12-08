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

public class AddMenuActivity extends AppCompatActivity {
    //Widgets and firebase
    Button btnDiscard;
    Button btnSubmit;
    EditText inputMealName, inputMealType, inputCuisineType, inputListOfIngredients , inputPrice, inputAllergens,edtTxtDescribe;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference appDatabaseReference;
    private String chefID;
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
        inputAllergens = findViewById(R.id.inputAllergens);
        edtTxtDescribe = findViewById(R.id.edtTxtDescribe);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        chefID = currentUser.getEmail().replace('.', '~');

        //Firebase Set-Up
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        appDatabaseReference = FirebaseDatabase.getInstance().getReference();

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
                    try {
                        String name = inputMealName.getText().toString();
                        String cuisineType = inputCuisineType.getText().toString();
                        String type = inputMealType.getText().toString();
                        String ingredients = inputListOfIngredients.getText().toString();
                        String price = inputPrice.getText().toString();
                        String allergens = inputAllergens.getText().toString();
                        String description = edtTxtDescribe.getText().toString();
                        Meal newMeal = new Meal(name, price, type, cuisineType, ingredients, allergens, description, false, currentUser.getEmail().toString());
                        createMenuItem(newMeal);
                        startActivity(new Intent( AddMenuActivity.this, EditMenuActivity.class));

                    }catch(Exception e){
                        Toast.makeText(AddMenuActivity.this, "Failed Making Menu Item", Toast.LENGTH_SHORT).show();
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
            inputMealType.setError("Field is Mandatory");
            returnVal = false;
        }
        if(price.equals("")){
            inputPrice.setError("Field is Mandatory");
            returnVal = false;
        }

        return returnVal;
    }

    private void createMenuItem(Meal newMeal){


        if(addMenuItemToDB(newMeal)) {
            Toast.makeText(AddMenuActivity.this, "Created Menu Item Successfully", Toast.LENGTH_SHORT).show();
        } else{
            // If creating complaint in fails, display a message to the user.

            Toast.makeText(AddMenuActivity.this, "Failed to create Menu Item.", Toast.LENGTH_SHORT).show();
        }



    }

    public boolean addMenuItemToDB(Meal newMeal){

        try {
            appDatabaseReference.child("meals").child(chefID+"_"+newMeal.getMealName()).setValue(newMeal);
            return  true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}