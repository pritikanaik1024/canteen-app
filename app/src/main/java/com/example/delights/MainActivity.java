package com.example.delights;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        handler = new Handler();

        // Delay for splash screen effect
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUserStatus();
            }
        }, 2000);
    }

    private void checkUserStatus() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in, verify if the user still exists in the database
            checkIfUserExists(currentUser.getUid());
        } else {
            // No user is signed in, navigate to Fetch activity
            navigateTo(Fetch.class);
        }
    }

    private void checkIfUserExists(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").document(userId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            // User exists in the database, navigate to Homescreen
                            navigateTo(Homescreen.class);
                        } else {
                            // User does not exist, sign out and navigate to Fetch activity
                            auth.signOut();
                            navigateTo(Fetch.class);
                        }
                    } else {
                        // Error occurred while checking user, navigate to Fetch activity
                        auth.signOut();
                        navigateTo(Fetch.class);
                    }
                });
    }

    private void navigateTo(Class<?> destination) {
        Intent intent = new Intent(MainActivity.this, destination);
        startActivity(intent);
        finish();
    }
}
