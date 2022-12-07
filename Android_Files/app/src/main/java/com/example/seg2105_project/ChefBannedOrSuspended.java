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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
                String dbIdEmail = currentUser.getEmail().replace('.', '~');

                boolean isBanned = snapshot.child(dbIdEmail).child("banned").getValue(Boolean.class);
                int lengthOfSuspension = snapshot.child(dbIdEmail).child("suspensionLength").getValue(Integer.class);
                if(isBanned){
                    txtMessage.setText("You are banned");
                }else{
                    txtMessage.setText("You are suspended until " + dateAfterNumDays(lengthOfSuspension));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("________DB ERRR________");
            }
        });



    }

    private String dateAfterNumDays(int numDays) {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = "";

        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DATE, numDays);
        formattedDate = dateFormat.format(c.getTime());

        return formattedDate;
    }
}