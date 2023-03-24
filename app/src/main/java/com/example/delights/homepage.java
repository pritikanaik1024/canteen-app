package com.example.delights;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homepage extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.cart_nav:
                        startActivity(new Intent(getApplicationContext(),pay.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.token_nav:
                        startActivity(new Intent(getApplicationContext(),token.class));
                        overridePendingTransition(0,0);
                        break;

                    case R.id.profile_nav:
                        startActivity(new Intent(getApplicationContext(),profile_home.class));
                        overridePendingTransition(0,0);
                        break;

                }

            }
        });



    }
}