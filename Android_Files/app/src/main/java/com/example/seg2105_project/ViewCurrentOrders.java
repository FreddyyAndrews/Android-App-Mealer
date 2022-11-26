package com.example.seg2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewCurrentOrders extends AppCompatActivity {

    Button btnReturnToConsole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_current_orders);
        btnReturnToConsole = findViewById(R.id.btnReturnToConsole);

        btnReturnToConsole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( ViewCurrentOrders.this, ChefActivity.class));
            }
        });
    }
}