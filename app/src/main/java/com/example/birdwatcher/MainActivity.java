package com.example.birdwatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonSubmit, goToSearch;
    EditText birdName, zipCode, personName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSubmit = findViewById(R.id.buttonSubmit);
        goToSearch = findViewById(R.id.goToSearch);
        birdName = findViewById(R.id.birdName);
        zipCode = findViewById(R.id.zipCode);
        personName = findViewById(R.id.personName);

        buttonSubmit.setOnClickListener(this);
        goToSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Birds");

        if(buttonSubmit == view) {

            String reportBird = birdName.getText().toString();
            int reportLocation = Integer.parseInt(zipCode.getText().toString());
            String reportedbyPerson = personName.getText().toString();

            Bird myBird = new Bird(reportBird, reportLocation, reportedbyPerson);

            myRef.push().setValue(myBird);

        } else if(goToSearch == view) {

            Intent searchIntent = new Intent(this, SearchActivity.class);
            startActivity(searchIntent);

        }
    }
}
