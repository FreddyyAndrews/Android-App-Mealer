package com.example.seg2105_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Stack;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HandleComplaint extends AppCompatActivity {
    Button btnBan, btnDismiss, btnSuspend;
    EditText txtLengthOfSuspension;
    Complaint complaint;
    TextView txtChefName;
    TextView txtChefEmail;
    TextView txtDescription;
    private DatabaseReference appDatabaseReference;
    Stack<Complaint> complaintList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_complaint);
        btnBan = (Button) findViewById(R.id.btnBan);
        btnSuspend = (Button) findViewById(R.id.btnSuspend);
        btnDismiss = (Button) findViewById(R.id.btnDismiss);
        txtLengthOfSuspension = (EditText) findViewById(R.id.txtLengthOfSuspension);
        complaintList = new Stack<Complaint>();

        appDatabaseReference = FirebaseDatabase.getInstance().getReference();
        appDatabaseReference.child("complaints").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Complaint temp;
                        for (DataSnapshot complaintSnapshot : dataSnapshot.getChildren()) {
                            temp = new Complaint(complaintSnapshot.child("complaintMessage").getValue().toString(), complaintSnapshot.child("email").getValue().toString());
                            complaintList.push(temp);
                        }
                        Complaint complaint = complaintList.peek();
                        populateFields(complaint);


                        btnBan.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String bannedChef = complaint.getEmail();
                                String bannedChefId = bannedChef.replace('.', '~');
                                appDatabaseReference.child("people").child(bannedChefId).addListenerForSingleValueEvent(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            Chef updatedChef = new Chef(
                                              dataSnapshot.child("firstName").getValue().toString(),
                                              dataSnapshot.child("lastName").getValue().toString(),
                                              dataSnapshot.child("email").getValue().toString(),
                                              dataSnapshot.child("address").getValue().toString(),
                                              dataSnapshot.child("type").getValue().toString()
                                            );
                                            updatedChef.setBanned(true);
                                            updatedChef.setDescription(dataSnapshot.child("description").getValue().toString());
                                            updatedChef.setRating(Double.parseDouble(dataSnapshot.child("rating").getValue().toString()));
                                            updatedChef.setNumMealsSold(Integer.parseInt(dataSnapshot.child("numMealsSold").getValue().toString()));

                                            appDatabaseReference.child("people").child(bannedChefId).setValue(updatedChef);
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            //handle databaseError
                                        }
                                });
                                //bannedChef.setBanned(true);
//                                startActivity(new Intent( HandleComplaint.this, AdminActivity.class));
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
                                    int suspentionLength = Integer.parseInt(txtLengthOfSuspension.getText().toString());
                                    dateAfterNumDays(suspentionLength);
                                    // TODO: Update with real db values
                                    // TODO: Update db once complaint is dealt with
//                                    Chef suspendedChef = complaint.getAssociatedChef();
//                                    suspendedChef.setSuspensionLength(Integer.parseInt(txtLengthOfSuspension.getText().toString()));
//                                    startActivity(new Intent( HandleComplaint.this, AdminActivity.class));
                                }

                            }
                        });


                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

    private boolean validateSuspension(){
        Boolean isValid = true;
        String suspentionLength = txtLengthOfSuspension.getText().toString();
        double nbSuspentionLength = 0;
        if (!suspentionLength.isEmpty()) {
            nbSuspentionLength = Double.parseDouble(suspentionLength);
        }
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

    private void populateFields(Complaint complaint) {
        txtChefName = (TextView) findViewById(R.id.txtChefName);
        txtChefEmail = (TextView) findViewById(R.id.txtChefEmail);
        txtDescription = (TextView) findViewById(R.id.txtDescription);

        //txtChefName.setText("Chef Name: " + complaint.getAssociatedChef().firstName + " " + complaint.getAssociatedChef().lastName);
        txtChefEmail.setText("Chef Email: " + complaint.getEmail());
        txtDescription.setText(complaint.getComplaintMessage());
    }
}