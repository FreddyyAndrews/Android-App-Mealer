package com.example.seg2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Stack;

public class OrderActivity extends AppCompatActivity {

    Button btnSearch, btnBack;
    EditText searchBar;
    Switch chooser;
    private DatabaseReference appDatabaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    Stack<Meal> mealList = new Stack<Meal>();
    ListView viewMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        btnSearch = findViewById(R.id.btnSearch);
        btnBack = findViewById(R.id.btnBack);
        searchBar = findViewById(R.id.searchBar);
        appDatabaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        viewMenu = findViewById(R.id.viewMenu);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( OrderActivity.this, ClientActivity.class));
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
    }
    public void search(){
        String search = searchBar.getText().toString().toLowerCase();

        appDatabaseReference.child("meals").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Meal temp;
                        ArrayList complaintListDisplay = new ArrayList();
                        for(DataSnapshot mealSnapshot : dataSnapshot.getChildren()) {
                            temp = new Meal(mealSnapshot.child("mealName").getValue().toString(), mealSnapshot.child("price").getValue().toString(),mealSnapshot.child("mealType").getValue().toString(),mealSnapshot.child("cuisineType").getValue().toString(),mealSnapshot.child("listOfIngredients").getValue().toString(),mealSnapshot.child("allergens").getValue().toString(),mealSnapshot.child("description").getValue().toString(),(Boolean) mealSnapshot.child("currentlyOffered").getValue(), mealSnapshot.child("associatedChefEmail").getValue().toString());
                            if(temp.getMealName().equals(search) && temp.getCurrentlyOffered()){
                                complaintListDisplay.add(temp.getMealName());
                                mealList.push(temp);
                            }

                        }

                        ArrayAdapter arrayAdapter = new ArrayAdapter(OrderActivity.this, R.layout.row, complaintListDisplay);
                        viewMenu.setAdapter(arrayAdapter);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

        viewMenu.setClickable(true);
        viewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                String tappedMeal = (String) viewMenu.getItemAtPosition(position);
                Intent i = new Intent(OrderActivity.this, OrderItemActivity.class);
                i.putExtra("key", tappedMeal);
                startActivity(i);

                /*
                String value="Hello world";
Intent i = new Intent(CurrentActivity.this, NewActivity.class);
i.putExtra("key",value);
startActivity(i);
Then in the new Activity, retrieve those values:

Bundle extras = getIntent().getExtras();
if (extras != null) {
    String value = extras.getString("key");
    //The key argument here must match that used in the other activity
}
                 */

            }
        });


    }
}