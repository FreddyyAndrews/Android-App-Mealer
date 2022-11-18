package com.example.seg2105_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChefBannedOrSuspended extends AppCompatActivity {
    Button btnSignOut;
    TextView txtMessage;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference appDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_banned_or_suspended);
        btnSignOut = findViewById(R.id.btnSignOut);
        txtMessage = findViewById(R.id.txtMessage);
        //Firebase
        appDatabaseReference = FirebaseDatabase.getInstance().getReference("people");
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent( ChefBannedOrSuspended.this, MainActivity.class));
            }
        });

        appDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String type = snapshot.child(currentUser.getUid()).child("type").getValue(String.class);
                String firstName = snapshot.child(currentUser.getUid()).child("firstName").getValue(String.class);
                String lastName = snapshot.child(currentUser.getUid()).child("lastName").getValue(String.class);
                String address = snapshot.child(currentUser.getUid()).child("addresss").getValue(String.class);
                String email = snapshot.child(currentUser.getUid()).child("email").getValue(String.class);
                boolean isBanned = snapshot.child(currentUser.getUid()).child("banned").getValue(Boolean.class);
                String lengthOfSuspension = snapshot.child(currentUser.getUid()).child("suspensionLength").getValue(Integer.class).toString();
                if(isBanned){
                    txtMessage.setText("You are banned");
                }else{
                    txtMessage.setText("You are suspended for " + lengthOfSuspension +" days." );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("________DB ERRR________");
            }
        });



    }
}