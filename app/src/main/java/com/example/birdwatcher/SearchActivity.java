package com.example.birdwatcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonSearch, buttonReport;
    EditText searchZip;
    TextView textViewPerson, textViewBird;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        buttonSearch = findViewById(R.id.buttonSearch);
        buttonReport = findViewById(R.id.buttonReport);
        searchZip = findViewById(R.id.searchZip);

        textViewPerson = findViewById(R.id.textViewPerson);
        textViewBird = findViewById(R.id.textViewBird);

        buttonSearch.setOnClickListener(this);
        buttonReport.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Birds");

        myRef.setValue("Hello, World!");

        if(buttonSearch == view) {

            /*myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String value = dataSnapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                }
            });*/

            final int findBird = Integer.parseInt(searchZip.getText().toString());

            myRef.orderByChild("zipcode").equalTo(findBird).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String findKey = dataSnapshot.getKey();
                    Bird findReport = dataSnapshot.getValue(Bird.class);

                    textViewBird.setText("Most recently reported bird: " + findReport.birdname);
                    textViewPerson.setText("Reported by: " + findReport.personname);

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else if(buttonReport == view) {

            Intent reportIntent = new Intent(this, MainActivity.class);
            startActivity(reportIntent);

        }
    }
}
