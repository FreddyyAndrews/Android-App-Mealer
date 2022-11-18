package com.example.seg2105_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import android.widget.Toast;


public class RegisterChef extends AppCompatActivity {
    //Widgets
    Button btnBack;
    Button btnVoidCheque;
    Button btnRegister;
    EditText edtTxtDescribe;
    //Instance variables
    int SELECT_PICTURE = 200;
    private  Drawable voidCheque;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference appDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Widget set-up
        setContentView(R.layout.activity_register_chef);
        btnBack = findViewById(R.id.btnBack);
        edtTxtDescribe = findViewById(R.id.edtTxtDescribe);
        btnVoidCheque = findViewById(R.id.btnVoidCheque);
        btnRegister= findViewById(R.id.btnRegister);
        //Firebase set-up
        appDatabaseReference = FirebaseDatabase.getInstance().getReference("people");
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        //Back button on click
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( RegisterChef.this, RegisterActivity.class));
            }
        });
        //Void cheque on click
        btnVoidCheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Calling method that allows user to select image from gallery
                imageChooser();
            }
        });
        //On click for submission
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String description = edtTxtDescribe.getText().toString();
                if(addChefSpecificInfoToDB(description,voidCheque)){
                    Toast.makeText(RegisterChef.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent( RegisterChef.this, LoggedInActivity.class));
                }else{
                    Toast.makeText(RegisterChef.this, "Failed to update", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
    //Adds chefs description to db
    //TODO add chefs void cheque to db
    public boolean addChefSpecificInfoToDB(String description, Drawable voidCheque) {

       try{
           appDatabaseReference.child(currentUser.getUid()).child("description").setValue(description);
           return true;
       }catch(Exception e){
           return false;
       }
    }

    void imageChooser() {

        //Selecting picture from android gallery

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Processing picture result
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the

            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                        voidCheque = Drawable.createFromStream(inputStream, selectedImageUri.toString() );
                    } catch (FileNotFoundException e) {
                        voidCheque = getResources().getDrawable(R.drawable.ic_baseline_image_24);
                    }

                    btnVoidCheque.setBackground(voidCheque);
                }
            }
        }
    }

    // Don't allow user to use the "back" arrow android os feature
    @Override
    public void onBackPressed() {
        return;
    }
}