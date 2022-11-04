package com.example.seg2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HandleComplaint extends AppCompatActivity {
    Button btnBan, btnDismiss, btnSuspend;
    EditText txtLengthOfSuspension;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_complaint);
        btnBan = (Button) findViewById(R.id.btnBan);
        btnSuspend = (Button) findViewById(R.id.btnSuspend);
        btnDismiss = (Button) findViewById(R.id.btnDismiss);
        txtLengthOfSuspension = (EditText) findViewById(R.id.txtLengthOfSuspension);

        btnBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( HandleComplaint.this, AdminActivity.class));
            }
        });

        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( HandleComplaint.this, AdminActivity.class));
            }
        });

        btnSuspend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateSuspension()){
                    startActivity(new Intent( HandleComplaint.this, AdminActivity.class));
                }

            }
        });
    }

    private boolean validateSuspension(){
        Boolean isValid = true;
        String susLength = txtLengthOfSuspension.getText().toString();
        if(susLength.isEmpty()){
            isValid=false;
            txtLengthOfSuspension.setError("Field is Mandatory");
        }
        try{
            int tester = Integer.parseInt(susLength);
            if(tester<1){
                isValid=false;
                txtLengthOfSuspension.setError("Enter positive number");
            }
        }catch(NumberFormatException e){
            isValid=false;
            txtLengthOfSuspension.setError("Enter valid number");
        }

        return isValid;
    }
}