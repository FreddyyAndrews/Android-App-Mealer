package com.example.seg2105_project;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MakeComplaintActivity extends AppCompatActivity {

    Button btnSubmit, btnBack;
    EditText edtTxtDescribe,inputChefName, inputChefEmail;
    private DatabaseReference appDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_complaint);

        btnBack = findViewById(R.id.btnBack);
        btnSubmit = findViewById(R.id.btnSubmit);
        edtTxtDescribe = findViewById(R.id.edtTxtDescribe);
        inputChefEmail = findViewById(R.id.inputChefEmail);
        inputChefName = findViewById(R.id.inputChefName);
        appDatabaseReference = FirebaseDatabase.getInstance().getReference();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( MakeComplaintActivity.this, ClientActivity.class));
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
    }

    private void validate(){
        boolean checker = true;

        String chefEmail = inputChefEmail.getText().toString();
        String chefID = chefEmail.replace('.', '~');
        String description = edtTxtDescribe.getText().toString();
        checker = true;

        if(chefEmail.equals("")){
            checker = false;
            inputChefEmail.setError("Field is Mandatory");
        }
        if(description.equals("")){
            checker = false;
            edtTxtDescribe.setError("Field is Mandatory");
        }

        if(checker){

            appDatabaseReference.child("people").child(chefID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (!snapshot.exists()) {

                        inputChefEmail.setError("Enter Valid Chef Email");
                    }else{
                        createComplaint();
                        startActivity(new Intent( MakeComplaintActivity.this, ClientActivity.class));
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    inputChefEmail.setError("Enter Valid Chef Email");
                }



            });
        }

    }


    private void createComplaint(){

        String chefEmail = inputChefEmail.getText().toString();
        String description = edtTxtDescribe.getText().toString();

        if(addComplaintToDB(chefEmail, description)) {
            Toast.makeText(MakeComplaintActivity.this, "Submitted Complaint successfully", Toast.LENGTH_SHORT).show();
        } else{
            // If creating complaint in fails, display a message to the user.

            Toast.makeText(MakeComplaintActivity.this, "Failed to create new user.", Toast.LENGTH_SHORT).show();
        }



    }

    public boolean addComplaintToDB(String chefEmail, String description){
        String dbIdEmail = chefEmail.replace('.', '~');
        Complaint newComplaint = new Complaint( description, chefEmail);
        try {
            appDatabaseReference.child("complaints").child(dbIdEmail).setValue(newComplaint);
            return  true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}