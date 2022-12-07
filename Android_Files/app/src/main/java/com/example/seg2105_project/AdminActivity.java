package com.example.seg2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;


public class AdminActivity extends AppCompatActivity {

    private DatabaseReference appDatabaseReference;

    ListView viewComplaints;
    Button btnHandleComplaint, btnLogout;
    Stack<Complaint> complaintList = new Stack<Complaint>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        viewComplaints = (ListView) findViewById(R.id.viewComplaints);
        btnHandleComplaint = (Button) findViewById(R.id.btnHandleComplaint);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        appDatabaseReference = FirebaseDatabase.getInstance().getReference();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent( AdminActivity.this, MainActivity.class));
            }
        });

        btnHandleComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!complaintList.isEmpty()){
                    startActivity(new Intent( AdminActivity.this, HandleComplaint.class));
                }
            }
        });

        // Get complaint list from DB
        appDatabaseReference.child("complaints").addListenerForSingleValueEvent(
            new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Complaint temp;
                    ArrayList complaintListDisplay = new ArrayList();
                    for(DataSnapshot complaintSnapshot : dataSnapshot.getChildren()) {
                        temp = new Complaint(complaintSnapshot.child("complaintMessage").getValue().toString(), complaintSnapshot.child("email").getValue().toString());
                        complaintListDisplay.add(temp.getEmail());
                        complaintList.push(temp);
                    }

                   ArrayAdapter arrayAdapter = new ArrayAdapter(AdminActivity.this, R.layout.row, complaintListDisplay);
                    viewComplaints.setAdapter(arrayAdapter);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //handle databaseError
                }
            });
    }
}