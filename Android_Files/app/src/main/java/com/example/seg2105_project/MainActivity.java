package com.example.seg2105_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    Button btnRegister, btnLogin;
    EditText inputEmail, inputPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRegister= findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        inputEmail = findViewById(R.id.inputRegisterEmail);
        inputPassword = findViewById(R.id.inputRegisterPassword);

        firebaseAuth = FirebaseAuth.getInstance();


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( MainActivity.this, RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Validate the Login Before allowing the user to proceed
                String inEmail = inputEmail.getText().toString();
                String inPassword = inputPassword.getText().toString();

                if (inEmail == null || inEmail.length() == 0 || inPassword == null || inPassword.length() == 0) {
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(inEmail, inPassword)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                Toast.makeText(MainActivity.this, "Log in success!.",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent( MainActivity.this, LoggedInActivity.class));
                            } else {
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Incorrect email or password.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        });
    }

    // Don't allow user to use the "back" arrow android os feature
    @Override
    public void onBackPressed() {
        return;
    }
}