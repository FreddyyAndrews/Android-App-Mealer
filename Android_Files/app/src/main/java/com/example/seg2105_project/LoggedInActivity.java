package com.example.seg2105_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoggedInActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference appDatabaseReference;
    TextView welcomeMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String activity = intent.getStringExtra("activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        welcomeMsg = findViewById(R.id.welcomeText);


        appDatabaseReference = FirebaseDatabase.getInstance().getReference("people");

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        // Retreive data from db and show it to the user
        appDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String dbIdEmail = currentUser.getEmail().replace('.', '~');
                String type = snapshot.child(dbIdEmail).child("type").getValue(String.class);

                //Sends Chef to Chef activity
                if(type.equals("chef")){
                    if(snapshot.child(dbIdEmail).child("suspensionLength").getValue(Integer.class).equals(0) && !snapshot.child(dbIdEmail).child("banned").getValue(Boolean.class)){
                        startActivity(new Intent( LoggedInActivity.this, ChefActivity.class));
                    }else{
                        startActivity(new Intent( LoggedInActivity.this, ChefBannedOrSuspended.class));
                    }

                }else if(type.equals("client")){
                    startActivity(new Intent( LoggedInActivity.this, ClientActivity.class));
                }
                welcomeMsg.setText("Loading...");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("________DB ERRR________");
            }
        });
    }
}