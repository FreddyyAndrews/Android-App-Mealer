package com.example.seg2105_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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
    String ID ;
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
                        ID = complaint.getEmail().replace('.', '~');


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
                                            startActivity(new Intent( HandleComplaint.this, AdminActivity.class));
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Toast.makeText(HandleComplaint.this, "Failed to Ban Chef.", Toast.LENGTH_SHORT).show();
                                        }
                                });
                                deleteComplaintFromDB();
                            }
                        });

                        btnDismiss.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                deleteComplaintFromDB();
                                startActivity(new Intent( HandleComplaint.this, AdminActivity.class));
                            }
                        });

                        btnSuspend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (validateSuspension()){
                                    int suspensionLength = Integer.parseInt(txtLengthOfSuspension.getText().toString());

                                    String susChef = complaint.getEmail();
                                    String susChefId = susChef.replace('.', '~');
                                    appDatabaseReference.child("people").child(susChefId).addListenerForSingleValueEvent(
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
                                                    updatedChef.setBanned(false);
                                                    updatedChef.setSuspensionLength(suspensionLength);
                                                    updatedChef.setDescription(dataSnapshot.child("description").getValue().toString());
                                                    updatedChef.setRating(Double.parseDouble(dataSnapshot.child("rating").getValue().toString()));
                                                    updatedChef.setNumMealsSold(Integer.parseInt(dataSnapshot.child("numMealsSold").getValue().toString()));

                                                    appDatabaseReference.child("people").child(susChefId).setValue(updatedChef);
                                                    startActivity(new Intent( HandleComplaint.this, AdminActivity.class));
                                                }
                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                    Toast.makeText(HandleComplaint.this, "Failed to suspend chef.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                                deleteComplaintFromDB();
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

        txtChefEmail = (TextView) findViewById(R.id.txtChefEmail);
        txtDescription = (TextView) findViewById(R.id.txtDescription);


        txtChefEmail.setText("Chef Email: " + complaint.getEmail());
        txtDescription.setText(complaint.getComplaintMessage());
    }

    private void deleteComplaintFromDB(){
    //Deletes complaint from DB

        Query findComplaint = appDatabaseReference.child("complaints").child(ID);

        findComplaint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot complaintSnapshot: dataSnapshot.getChildren()) {
                    complaintSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HandleComplaint.this, "Failed to remove Complaint from DB.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}