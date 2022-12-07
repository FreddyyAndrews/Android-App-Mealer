package com.example.seg2105_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Stack;

public class HandleMealRequest extends AppCompatActivity {

    Button btnReturnToConsole;
    Button btnAccept;
    Button btnDecline;
    ListView viewRequests;
    Stack<Complaint> requestsList = new Stack<Complaint>();
    private DatabaseReference appDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_meal_request);
        btnReturnToConsole = findViewById(R.id.btnReturnToConsole);
        btnAccept = findViewById(R.id.btnAccept);
        btnDecline = findViewById(R.id.btnDecline);
        viewRequests = (ListView) findViewById(R.id.viewRequests);

        appDatabaseReference = FirebaseDatabase.getInstance().getReference();

        appDatabaseReference.child("requests").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot complaintSnapshot : dataSnapshot.getChildren()) {
//                    Complaint complaint = complaintSnapshot.getValue(Complaint.class);
//                    requestsList.push(complaint);
//                }
//                ArrayList complaintListDisplay = new ArrayList();
//                for (int i = 0; i < requestsList.size(); i++) {
//                    complaintListDisplay.add(requestsList.get(i).getAssociatedChef().email);
//                }
//                ArrayAdapter arrayAdapter = new ArrayAdapter(HandleMealRequest.this, R.layout.row, complaintListDisplay);
//                viewRequests.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnReturnToConsole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( HandleMealRequest.this, ChefActivity.class));
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO implement accepting meal requests
            }
        });

        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO implement decline meal request
            }
        });
    }
}