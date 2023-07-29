package com.example.cae2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class logIn extends AppCompatActivity {
    // Firebase Auth
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    //Declaring Widgets
    private EditText emailEt, passET;

    TextView createBTN,tv ;
    Button loginBTN,lookforJobs;

    //Firebase Authentication

    private FirebaseUser getCurrentUser;

    //Firebase Connection
    private FirebaseFirestore db = FirebaseFirestore.getInstance();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        //initializing widgets
        loginBTN = findViewById(R.id.email_sign_in_button);
        createBTN = findViewById(R.id.create_acc_BTN);
        emailEt = findViewById(R.id.email);
        passET = findViewById(R.id.password);
        lookforJobs = findViewById(R.id.WantedJobsbtn);




        //on click go to ssign up page
        createBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(logIn.this, ssignUp.class);
                startActivity(i);
            }
        });


lookforJobs.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent(logIn.this, MainActivity.JobVacancies.class);
        startActivity(i);
    }
});







    }




    }
