package com.example.delights;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Fetch extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Check if the user is already logged in
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            // User is logged in, navigate to HomeScreen or another relevant activity
            Intent intent = new Intent(Fetch.this, Homescreen.class);
            startActivity(intent);
            finish();
            return;
        }

        // Setup buttons for Register and Login
        Button Register = findViewById(R.id.Register);
        Button Login = findViewById(R.id.Login);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Fetch.this, Register.class);
                startActivity(a);
                finish();
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(Fetch.this, Login.class);
                startActivity(b);
            }
        });
    }
}
