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


public class AdminActivity extends AppCompatActivity {

    private DatabaseReference appDatabaseReference;

    ListView viewComplaints;
    Button btnHandleComplaint, btnLogout;
    ArrayList<Complaint> complaintList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        viewComplaints = (ListView) findViewById(R.id.viewComplaints);
        btnHandleComplaint = (Button) findViewById(R.id.btnHandleComplaint);
        btnLogout = (Button) findViewById(R.id.btnLogout);

//        appDatabaseReference = FirebaseDatabase.getInstance().getReference().child("complaints");
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

        //TODO get complaints from DB
        //This is just an example for testing

        appDatabaseReference.child("complaints").addListenerForSingleValueEvent(
            new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot complaintSnapshot : dataSnapshot.getChildren()) {
                        Complaint complaint = complaintSnapshot.getValue(Complaint.class);
                        System.out.println(complaint);
                    }

//                    for (int i = 1; i <= allComplaints.size(); i++) {
//                        complaintListDisplay.add(allComplaints.get(i).)
//                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //handle databaseError
                }
            });

//        User complaintChef = new User("bob", "John", "spitter@gmail.com", "5 Bob street", "chef");
//        Complaint testComplaint = new Complaint("Spat in my food", complaintChef);
//        complaintList.add(testComplaint);
//        complaintListDisplay.add(complaintChef.email);
//
//        complaintList.add(testComplaint);
//        complaintListDisplay.add(complaintChef.email);
//
//        complaintList.add(testComplaint);
//        complaintListDisplay.add(complaintChef.email);
//
//        complaintList.add(testComplaint);
//        complaintListDisplay.add(complaintChef.email);

//        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.row, complaintListDisplay);
//        viewComplaints.setAdapter(arrayAdapter);

    }
}