package com.example.seg2105_project;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChefActivity extends AppCompatActivity {

    Button btnEditMenu;
    Button btnEditMealList;
    Button btnSignOut;
    Button btnHandleMealRequest;
    Button btnViewOrders;
    Button btnSettings;
    TextView txtRating, txtDisplayMessage;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference appDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        appDatabaseReference = FirebaseDatabase.getInstance().getReference("people");
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);
        btnEditMealList = findViewById(R.id.btnEditMealList);
        btnEditMenu = findViewById(R.id.btnEditMenu);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnHandleMealRequest = findViewById(R.id.btnHandleMealRequest);
        btnViewOrders = findViewById(R.id.btnViewOrders);
        btnSettings = findViewById(R.id.btnSettings);
        txtRating = findViewById(R.id.txtRating);
        txtDisplayMessage = findViewById(R.id.txtDisplayMessage);

        appDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String type = snapshot.child(currentUser.getUid()).child("type").getValue(String.class);
                String firstName = snapshot.child(currentUser.getUid()).child("firstName").getValue(String.class);
                String lastName = snapshot.child(currentUser.getUid()).child("lastName").getValue(String.class);
                String address = snapshot.child(currentUser.getUid()).child("addresss").getValue(String.class);
                String email = snapshot.child(currentUser.getUid()).child("email").getValue(String.class);
                int rating = snapshot.child(currentUser.getUid()).child("rating").getValue(Integer.class);

                //welcomeMsg.setText("Welcome " + firstName + " you are signed-in as a " + type + "!");
                txtDisplayMessage.setText("Welcome "+ firstName);
                txtRating.setText("Current Rating: " + rating +"/5");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("________DB ERRR________");
            }
        });

        btnEditMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( ChefActivity.this, EditMenuActivity.class));
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent( ChefActivity.this, MainActivity.class));
            }
        });

        btnEditMealList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( ChefActivity.this, EditMealListActivity.class));
            }
        });

        btnHandleMealRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Sends user to handle meal request activity
                startActivity(new Intent( ChefActivity.this, HandleMealRequest.class));
            }
        });

        btnViewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( ChefActivity.this, ViewCurrentOrders.class));
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( ChefActivity.this, EditChefProfileActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        return;
    }
}