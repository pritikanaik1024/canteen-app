package com.example.delights;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    // Firebase declaration
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    // Creation of a final String to pass into the next activity
    public static final String EXTRA_NAME = "com.example.delights.extra.NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // All declarations
        Button registerButton = findViewById(R.id.register_2);
        EditText emailEditText = findViewById(R.id.register_email);
        EditText passwordEditText = findViewById(R.id.register_password);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Field declarations and conversion to string
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Checking conditions for email field
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Register.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Checking conditions for password field
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Register.this, "Please enter a valid password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Call for registration function
                registerUser(email, password);
            }
        });
    }

    // Registration function
    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Message to be shown on successful creation
                    Toast.makeText(Register.this, "You have been successfully registered", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this, profile.class);
                    // Field to be passed in the next activity
                    intent.putExtra(EXTRA_NAME, email);
                    startActivity(intent);
                    finish();
                } else {
                    // Message to be shown on unsuccessful creation
                    Toast.makeText(Register.this, "Please enter a valid email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
