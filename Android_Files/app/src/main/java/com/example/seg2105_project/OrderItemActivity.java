package com.example.seg2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderItemActivity extends AppCompatActivity {

    TextView textMealName, textChefName, textChefEmail, textChefRating, textMealPrice, textAllergens, textMealType,
    textCuisineType, textListOfIngredients, textDescription;
    private String nameOfMeal;
    Button btnBack, btnOrder;
    private DatabaseReference appDatabaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_item);
        textMealName = findViewById(R.id.textMealName);
        textChefName = findViewById(R.id.textChefName);
        textChefEmail = findViewById(R.id.textChefEmail);
        textChefRating = findViewById(R.id.textChefRating);
        textMealPrice = findViewById(R.id.textMealPrice);
        textAllergens = findViewById(R.id.textAllergens);
        textMealType = findViewById(R.id.textMealType);
        textCuisineType = findViewById(R.id.textCuisineType);
        textListOfIngredients = findViewById(R.id.textListOfIngredients);
        textDescription = findViewById(R.id.textDescription);
        btnBack = findViewById(R.id.btnBack);
        btnOrder = findViewById(R.id.btnOrder);
        appDatabaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String nameOfMeal = extras.getString("key");

        }

        appDatabaseReference.child("meals").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Meal temp;

                        for(DataSnapshot mealSnapshot : dataSnapshot.getChildren()) {
                            temp = new Meal(mealSnapshot.child("mealName").getValue().toString(), mealSnapshot.child("price").getValue().toString(),mealSnapshot.child("mealType").getValue().toString(),mealSnapshot.child("cuisineType").getValue().toString(),mealSnapshot.child("listOfIngredients").getValue().toString(),mealSnapshot.child("allergens").getValue().toString(),mealSnapshot.child("description").getValue().toString(),(Boolean) mealSnapshot.child("currentlyOffered").getValue(), mealSnapshot.child("associatedChefEmail").getValue().toString());
                            if(temp.getMealName().equals(nameOfMeal)){
                                textMealName.setText("Meal Name:  " + temp.getMealName());
                                textMealPrice.setText(textMealName.getText().toString() + " " + temp.getPrice());
                                textAllergens.setText(textMealName.getText().toString() + " " + temp.getAllergens());
                                textCuisineType.setText(textMealName.getText().toString() + " " + temp.getCuisineType());
                                //textChefRating.setText(textMealName.getText().toString() + " " + temp.getDescription());
                                //textChefEmail.setText(textMealName.getText().toString() + " " + temp.getPrice());
                                //textChefName.setText(textMealName.getText().toString() + " " + temp.getPrice());
                                textListOfIngredients.setText(textMealName.getText().toString() + " " + temp.getListOfIngredients());
                                textMealPrice.setText(textMealName.getText().toString() + " " + temp.getPrice());
                                textMealPrice.setText(textMealName.getText().toString() + " " + temp.getPrice());

                            }

                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

    }
}