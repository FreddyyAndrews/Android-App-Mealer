package com.example.seg2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EditMenuActivity extends AppCompatActivity {

    Button btnAddMenuItem;
    Button btnGoBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);
        btnAddMenuItem = findViewById(R.id.btnAddMenuItem);
        btnGoBack = findViewById(R.id.btnGoBack);

        btnAddMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO implement Adding Menu Items
            }
        });

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( EditMenuActivity.this, ChefActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        return;
    }
}