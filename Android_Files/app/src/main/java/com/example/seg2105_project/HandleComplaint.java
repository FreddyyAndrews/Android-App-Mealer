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
    Complaint complaint;
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
                Chef bannedChef = complaint.getAssociatedChef();
                bannedChef.setBanned(true);
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
                if (validateSuspension()){
                    startActivity(new Intent( HandleComplaint.this, AdminActivity.class));
                }

            }
        });
    }

    private boolean validateSuspension(){
        Boolean isValid = true;
        String suspentionLength = txtLengthOfSuspension.getText().toString();
        double nbSuspentionLength = Double.parseDouble(suspentionLength);

        if (suspentionLength.isEmpty()) {
            isValid = false;
            txtLengthOfSuspension.setError("Field is Mandatory");
        }

        if (nbSuspentionLength < 1) {
            isValid = false;
            txtLengthOfSuspension.setError("Enter positive number");
        }

        if (nbSuspentionLength > 365) {
            isValid = false;
            txtLengthOfSuspension.setError("You can't suspend a chef for more than a year. Ban if need be.");
        }

        return isValid;
    }
}