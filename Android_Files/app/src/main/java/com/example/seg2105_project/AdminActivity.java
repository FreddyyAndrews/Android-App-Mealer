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

import java.util.ArrayList;


public class AdminActivity extends AppCompatActivity {

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

        ArrayList<String> complaintListDisplay = new ArrayList<>();
        User complaintChef = new User("bob", "John", "spitter@gmail.com", "5 Bob street", "chef");
        Complaint testComplaint = new Complaint("Spat in my food", complaintChef);
        complaintList.add(testComplaint);
        complaintListDisplay.add(complaintChef.email);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, complaintListDisplay);
        viewComplaints.setAdapter(arrayAdapter);


    }
}