package com.example.cae2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profilePage extends AppCompatActivity {

    FirebaseAuth auth;
    Button button;
    TextView textView;

    FirebaseUser user;

    ImageView facebook, instagram;

    // Firebase Auth
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        //import widgets
        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout);
        textView = findViewById(R.id.userDetails);
        user = auth.getCurrentUser();
        instagram = findViewById(R.id.instagram);
        facebook = findViewById(R.id.facebook);

        // Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();


        if (user ==null){
            Intent intent = new Intent(getApplicationContext(), logIn.class);
            startActivity(intent);
            finish();
        }else {
            textView.setText(user.getEmail());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), logIn.class);
                startActivity(intent);
                finish();
            }
        });

        //facebook implicit intent
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an implicit intent to view a web URL
                Intent intent = new Intent(Intent.ACTION_VIEW);
                // Set the data for the intent to the specified URL
                intent.setData(Uri.parse("https://www.facebook.com/CreditAgricoleEG/")); //uri class and parse
                // Start the activity to handle the intent                  //is used to covert to
                startActivity(intent);
            }
        });

        //instagram implicit intent

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an implicit intent to view a web URL
                Intent intent = new Intent(Intent.ACTION_VIEW);
                // Set the data for the intent to the specified URL
                intent.setData(Uri.parse("https://www.instagram.com/creditagricoleeg/?hl=en")); //uri class and parse
                // Start the activity to handle the intent                  //is used to covert to
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        currentUser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}