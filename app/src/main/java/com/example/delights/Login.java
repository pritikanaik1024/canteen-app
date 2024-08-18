package com.example.delights;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Login extends AppCompatActivity {
    // Firebase instances
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI elements
        EditText email = findViewById(R.id.login_email);
        EditText password = findViewById(R.id.login_password);
        Button loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInput = email.getText().toString().trim();
                String passwordInput = password.getText().toString().trim();

                if (validateInput(emailInput, passwordInput)) {
                    login(emailInput, passwordInput);
                }
            }
        });
    }

    private boolean validateInput(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(Login.this, "Please enter your email.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(Login.this, "Please enter your password.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void login(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    checkAdmin();
                } else {
                    Toast.makeText(Login.this, "Login unsuccessful. Please check your credentials or register.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkAdmin() {
        DocumentReference userDoc = db.collection("Users").document(auth.getUid());
        userDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String isAdmin = documentSnapshot.getString("is_admin");
                    if (Objects.equals(isAdmin, "1")) {
                        navigateTo(AdminDashboard.class, "Successfully logged in as Admin");
                    } else if (Objects.equals(isAdmin, "0")) {
                        navigateTo(PostLogin.class, "Login Successful");
                    } else {
                        Toast.makeText(Login.this, "User role is not defined", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "User data not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void navigateTo(Class<?> destination, String message) {
        Intent intent = new Intent(Login.this, destination);
        startActivity(intent);
        finish();
        Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
    }
}
