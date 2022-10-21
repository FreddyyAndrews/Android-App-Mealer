package com.example.seg2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoggedInActivity extends AppCompatActivity {

    //Todo give wlecome message as stated in deliverable instructions
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    TextView welcomeMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        welcomeMsg = findViewById(R.id.welcomeText);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        System.out.println(currentUser.getEmail());
        welcomeMsg.setText("Welcome " + currentUser.getEmail() + " you are signed-in as ");
    }
}