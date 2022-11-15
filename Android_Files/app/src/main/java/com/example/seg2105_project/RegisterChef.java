package com.example.seg2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class RegisterChef extends AppCompatActivity {

    Button btnBack;
    Button btnVoidCheque;
    Button btnRegister;
    int SELECT_PICTURE = 200;
    private  Drawable voidCheque;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_chef);
        btnBack = findViewById(R.id.btnBack);
        btnVoidCheque = findViewById(R.id.btnVoidCheque);
        btnRegister= findViewById(R.id.btnRegister);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( RegisterChef.this, RegisterActivity.class));
            }
        });

        btnVoidCheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Calling method that allows user to select image from gallery
                imageChooser();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( RegisterChef.this, LoggedInActivity.class));
            }
        });

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