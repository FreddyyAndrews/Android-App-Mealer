package com.example.seg2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Stack;

public class EditMenuActivity extends AppCompatActivity {

    Button btnAddMenuItem;
    Button btnGoBack;
    ListView viewMenu;
    private DatabaseReference appDatabaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    Stack<Meal> mealList = new Stack<Meal>();
    private String chefID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);
        btnAddMenuItem = findViewById(R.id.btnAddMenuItem);
        btnGoBack = findViewById(R.id.btnGoBack);
        viewMenu = findViewById(R.id.viewMenu);
        appDatabaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        chefID = currentUser.getEmail().replace('.', '~');
        btnAddMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( EditMenuActivity.this, AddMenuActivity.class));
            }
        });

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( EditMenuActivity.this, ChefActivity.class));
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
                            if(temp.getAssociatedChefEmail().equals(currentUser.getEmail())){
                                complaintListDisplay.add(temp.getMealName());
                                mealList.push(temp);
                            }

                        }

                        ArrayAdapter arrayAdapter = new ArrayAdapter(EditMenuActivity.this, R.layout.row, complaintListDisplay);
                        viewMenu.setAdapter(arrayAdapter);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

        viewMenu.setClickable(true);
        viewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                String tappedMeal = (String) viewMenu.getItemAtPosition(position);

                appDatabaseReference.child("meals").child(chefID +"_"+ tappedMeal).addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Meal updatedMeal = new Meal(
                                        dataSnapshot.child("mealName").getValue().toString(),
                                        dataSnapshot.child("price").getValue().toString(),
                                        dataSnapshot.child("mealType").getValue().toString(),
                                        dataSnapshot.child("cuisineType").getValue().toString(),
                                        dataSnapshot.child("listOfIngredients").getValue().toString(),
                                        dataSnapshot.child("allergens").getValue().toString(),
                                        dataSnapshot.child("description").getValue().toString(),
                                        (Boolean) dataSnapshot.child("currentlyOffered").getValue(),
                                        dataSnapshot.child("associatedChefEmail").getValue().toString()

                                );
                                if(updatedMeal.getCurrentlyOffered()){
                                    updatedMeal.setCurrentlyOfferedState(false);
                                    Toast.makeText(EditMenuActivity.this, "Meal Removed from Meal List", Toast.LENGTH_SHORT).show();
                                }else{
                                    updatedMeal.setCurrentlyOfferedState(true);
                                    Toast.makeText(EditMenuActivity.this, "Meal Added to Meal List", Toast.LENGTH_SHORT).show();
                                }

                                appDatabaseReference.child("meals").child(chefID +"_"+ tappedMeal).setValue(updatedMeal);

                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(EditMenuActivity.this, "Failed toggle meal list", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


    }

    @Override
    public void onBackPressed() {
        return;
    }
}