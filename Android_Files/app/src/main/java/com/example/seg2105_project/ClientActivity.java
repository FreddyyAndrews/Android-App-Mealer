package com.example.seg2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ClientActivity extends AppCompatActivity {

    Button btnOrder, btnSignOut, btnViewPendingOrders, btnMakeAComplaint, btnChangePaymentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        btnOrder = findViewById(R.id.btnOrder);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnViewPendingOrders = findViewById(R.id.btnViewPendingOrders);
        btnMakeAComplaint = findViewById(R.id.btnMakeAComplaint);
        btnChangePaymentInfo = findViewById(R.id.btnChangePaymentInfo);

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( ClientActivity.this, OrderActivity.class));
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent( ClientActivity.this, MainActivity.class));
            }
        });

        btnViewPendingOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( ClientActivity.this, ViewPendingOrdersActivity.class));
            }
        });

        btnMakeAComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( ClientActivity.this, MakeComplaintActivity.class));
            }
        });

        btnChangePaymentInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( ClientActivity.this, ChangePaymentInfoActivity.class));
            }
        });

    }
}