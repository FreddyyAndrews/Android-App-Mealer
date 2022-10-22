package com.example.seg2105_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.SymbolTable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;

public class LoggedInActivity extends AppCompatActivity {

    //Todo give wlecome message as stated in deliverable instructions
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference appDatabaseReference;
    TextView welcomeMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        welcomeMsg = findViewById(R.id.welcomeText);

        appDatabaseReference = FirebaseDatabase.getInstance().getReference("users");

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        // Retreive data from db and show it to the user
        appDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String type = snapshot.child(currentUser.getUid()).child("type").getValue(String.class);
                String firstName = snapshot.child(currentUser.getUid()).child("firstName").getValue(String.class);
                String lastName = snapshot.child(currentUser.getUid()).child("lastName").getValue(String.class);
                String address = snapshot.child(currentUser.getUid()).child("addresss").getValue(String.class);
                String email = snapshot.child(currentUser.getUid()).child("email").getValue(String.class);

                welcomeMsg.setText("Welcome " + firstName + " you are signed-in as a " + type + "!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("________DB ERRR________");
            }
        });
    }
}