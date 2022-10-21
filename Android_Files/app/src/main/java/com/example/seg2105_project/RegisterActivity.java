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
    EditText inputFirstName, inputLastName, inputEmail, inputPassword, inputPassword2, inputAddress, inputCity, inputProvince, inputCountry;
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
        inputCity = findViewById(R.id.inputCity);
        inputProvince = findViewById(R.id.inputProvence);
        inputCountry = findViewById(R.id.inputCountry);

        firebaseAuth = FirebaseAuth.getInstance();
        appDatabaseReference = FirebaseDatabase.getInstance().getReference();

        btnRegisterChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    type = "chef";
                    registerNewUser();

                }

            }
        });

        btnRegisterUser.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate()){
                    type ="user";
                    registerNewUser();

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

    public void registerNewUser() {
        String inFName = inputFirstName.getText().toString();
        String inLName = inputLastName.getText().toString();
        String inEmail = inputEmail.getText().toString();
        String inPassword = inputPassword.getText().toString();
        String inAddress = inputAddress.getText().toString();
        String inCity = inputCity.getText().toString();
        String inProvince = inputProvince.getText().toString();
        String inCountry = inputCountry.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(inEmail, inPassword)
            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if(addUserInfoToDB(user.getUid(), inFName, inLName, inEmail, inAddress, inCity, inProvince, inCountry, type)) {
                            Toast.makeText(RegisterActivity.this, "User Created successfully", Toast.LENGTH_SHORT).show();


                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
//                                    updateUI(null);
                    }
                }
            });
    }

    public boolean addUserInfoToDB(String id, String fName, String lName, String email, String address, String city, String province, String country, String type) {
        User newUser = new User(fName, lName, email, address, city, country, province, type);
        try {
            appDatabaseReference.child("users").child(id).setValue(newUser);
            return  true;
        } catch (Exception e) {
            return false;
        }
    }

    // Don't allow user to use the "back" arrow android os feature
    @Override
    public void onBackPressed() {
        return;
    }

    private boolean validate(){

        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPassword = inputPassword2.getText().toString();
        String address = inputAddress.getText().toString();
        String provence = inputProvince.getText().toString();
        String country = inputCountry.getText().toString();
        String firstName = inputFirstName.getText().toString();
        String lastName = inputLastName.getText().toString();
        String city = inputCity.getText().toString();

        if(!email.matches(emailPattern)){
            inputEmail.setError("Enter Valid Email Address");
        }else if(password.isEmpty() || password.length()<6){
            inputPassword.setError("Input a valid password");
        }else if(!password.equals(confirmPassword)){
            inputPassword2.setError("Passwords do not Match");
        }else if(country.isEmpty() ){
            inputCountry.setError("Field is Mandatory");
        }else if(firstName.isEmpty() ){
            inputFirstName.setError("Field is Mandatory");
        }else if(address.isEmpty()){
            inputAddress.setError("Field is Mandatory");
        }else if(provence.isEmpty()){
            inputProvince.setError("Field is Mandatory");
        }else if(city.isEmpty()){
            inputCity.setError("Field is Mandatory");
        }else if(lastName.isEmpty()){
            inputLastName.setError("Field is Mandatory");
        }else{
            return true;
        }
        return false;
    }
}