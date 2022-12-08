package com.example.seg2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Stack;

public class EditMealListActivity extends AppCompatActivity {

    Button btnGoBack;
    private DatabaseReference appDatabaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    Stack<Meal> mealList = new Stack<Meal>();
    private String chefID;
    ListView viewMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meal_list);
        btnGoBack = findViewById(R.id.btnGoBack);
        viewMenu = findViewById(R.id.viewMenu);
        appDatabaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( EditMealListActivity.this, ChefActivity.class));
            }
        });

        appDatabaseReference.child("meals").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Meal temp;
                        ArrayList complaintListDisplay = new ArrayList();
                        for(DataSnapshot mealSnapshot : dataSnapshot.getChildren()) {
                            temp = new Meal(mealSnapshot.child("mealName").getValue().toString(), mealSnapshot.child("price").getValue().toString(),mealSnapshot.child("mealType").getValue().toString(),mealSnapshot.child("cuisineType").getValue().toString(),mealSnapshot.child("listOfIngredients").getValue().toString(),mealSnapshot.child("allergens").getValue().toString(),mealSnapshot.child("description").getValue().toString(),(Boolean) mealSnapshot.child("currentlyOffered").getValue(), mealSnapshot.child("associatedChefEmail").getValue().toString());
                            if(temp.getAssociatedChefEmail().equals(currentUser.getEmail()) && temp.getCurrentlyOffered()){
                                complaintListDisplay.add(temp.getMealName());
                                mealList.push(temp);
                            }

                        }

                        ArrayAdapter arrayAdapter = new ArrayAdapter(EditMealListActivity.this, R.layout.row, complaintListDisplay);
                        viewMenu.setAdapter(arrayAdapter);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }
}