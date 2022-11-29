package com.example.seg2105_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;


public class RegisterActivity extends AppCompatActivity {

    TextView txtHasAccount;
    Button btnRegisterChef, btnRegisterUser;
    EditText inputFirstName, inputLastName, inputEmail, inputPassword, inputPassword2, inputAddress;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference appDatabaseReference;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtHasAccount= findViewById(R.id.txtHasAccount);
        btnRegisterChef = findViewById(R.id.btnRegisterChef);
        btnRegisterUser = findViewById(R.id.btnRegisterUser);
        inputFirstName = findViewById(R.id.inputFirstName);
        inputLastName = findViewById(R.id.inputLastName);
        inputEmail = findViewById(R.id.inputRegisterEmail);
        inputPassword = findViewById(R.id.inputRegisterPassword);
        inputPassword2 = findViewById(R.id.inputRegisterPassword2);
        inputAddress = findViewById(R.id.inputAddress);

        firebaseAuth = FirebaseAuth.getInstance();
        appDatabaseReference = FirebaseDatabase.getInstance().getReference();

        btnRegisterChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    registerNewUser("chef");
                }
            }
        });

        btnRegisterUser.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    registerNewUser("client");
                }
            }
        }));

        txtHasAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( RegisterActivity.this, MainActivity.class));
            }
        });
    }

    public void registerNewUser(String type) {
        String inFName = inputFirstName.getText().toString();
        String inLName = inputLastName.getText().toString();
        String inEmail = inputEmail.getText().toString();
        String inPassword = inputPassword.getText().toString();
        String inAddress = inputAddress.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(inEmail, inPassword)
            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if(addUserInfoToDB(user.getUid() , inFName, inLName, inEmail, inAddress, type)) {
                            if (type == "chef")  {
                                startActivity(new Intent( RegisterActivity.this, RegisterChef.class));
                            } else {
                                startActivity(new Intent( RegisterActivity.this, RegisterClient.class));
                            }
                            Toast.makeText(RegisterActivity.this, "User Created successfully", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterActivity.this, "Failed to create new user.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    public boolean addUserInfoToDB(String id, String firstName, String lastName, String email, String address, String type) {
        User newPerson = type == "chef" ? new Chef(firstName, lastName, email, address, type, id) : new Client(firstName, lastName, email, address, type, id);
        try {
            System.out.println(newPerson.id);
            appDatabaseReference.child("people").child(id).setValue(newPerson);
            return  true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean validate(){
        Boolean isValid = true;
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPassword = inputPassword2.getText().toString();
        String address = inputAddress.getText().toString();
        String firstName = inputFirstName.getText().toString();
        String lastName = inputLastName.getText().toString();

        if(!email.matches(emailPattern)) {
            inputEmail.setError("Enter Valid Email Address");
            isValid = false;
        }
        if(password.isEmpty() || password.length() < 6) {
            inputPassword.setError("Input a valid password");
            isValid = false;
        }
        if(!password.equals(confirmPassword)) {
            inputPassword2.setError("Passwords do not Match");
            isValid = false;
        }
        if(firstName.isEmpty()) {
            inputFirstName.setError("Field is Mandatory");
            isValid = false;
        }
        if(address.isEmpty()) {
            inputAddress.setError("Field is Mandatory");
            isValid = false;
        }
        if(lastName.isEmpty()) {
            inputLastName.setError("Field is Mandatory");
            isValid = false;
        }
        return isValid;
    }


    // Don't allow user to use the "back" arrow android os feature
    @Override
    public void onBackPressed() {
        return;
    }
}