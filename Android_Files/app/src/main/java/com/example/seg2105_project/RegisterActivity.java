package com.example.seg2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class RegisterActivity extends AppCompatActivity {

    TextView txtHasAccount;
    Button btnRegisterChef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtHasAccount= findViewById(R.id.txtHasAccount);
        btnRegisterChef = findViewById(R.id.btnRegisterChef);

        btnRegisterChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( RegisterActivity.this, RegisterChef.class));
            }
        });

        txtHasAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( RegisterActivity.this, MainActivity.class));
            }
        });
    }
}