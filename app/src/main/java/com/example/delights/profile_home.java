package com.example.delights;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class profile_home extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_home);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile_nav);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.cart_nav:
                        startActivity(new Intent(getApplicationContext(),pay.class));
                        finish();
                        overridePendingTransition(0,0);
                        break;
                    case R.id.token_nav:
                        startActivity(new Intent(getApplicationContext(),token.class));
                        finish();
                        overridePendingTransition(0,0);
                        break;

                    case R.id.profile_nav:
                        break;

                    case R.id.home_nav:
                        startActivity(new Intent(getApplicationContext(),homepage.class));
                        finish();
                        overridePendingTransition(0,0);
                        break;

                }

            }
        });


    }
}