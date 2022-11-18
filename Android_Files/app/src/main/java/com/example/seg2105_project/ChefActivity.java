package com.example.seg2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ChefActivity extends AppCompatActivity {

    Button btnEditMenu;
    Button btnEditMealList;
    Button btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);
        btnEditMealList = findViewById(R.id.btnEditMealList);
        btnEditMenu = findViewById(R.id.btnEditMenu);
        btnSignOut = findViewById(R.id.btnSignOut);

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


    }

    @Override
    public void onBackPressed() {
        return;
    }
}