package com.example.seg2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

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
                if(validate()){
                    //TODO Add complaint to DB
                    startActivity(new Intent( MakeComplaintActivity.this, ClientActivity.class));
                }
            }
        });
    }

    private boolean validate(){

        String chefName = inputChefName.getText().toString();
        String chefEmail = inputChefEmail.getText().toString();
        String description = edtTxtDescribe.getText().toString();
        boolean checker = true;

        if(chefName.equals("")){
            checker = false;
            inputChefName.setError("Field is Mandatory");
        }
        if(chefEmail.equals("")){
            checker = false;
            inputChefEmail.setError("Field is Mandatory");
        }
        if(description.equals("")){
            checker = false;
            edtTxtDescribe.setError("Field is Mandatory");
        }
        //TODO check if chef actually exists in db

        return checker;

    }
}