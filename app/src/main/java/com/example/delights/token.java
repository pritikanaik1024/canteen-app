package com.example.delights;

import static com.example.delights.pay.EXTRA_NUMBER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class token extends AppCompatActivity {

    TextView token ;
    BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        token = findViewById(R.id.token);


        bottomNavigationView.setSelectedItemId(R.id.token_nav);
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
                        break;

                    case R.id.profile_nav:
                        startActivity(new Intent(getApplicationContext(),profile_home.class));
                        finish();
                        overridePendingTransition(0,0);
                        break;

                    case R.id.home_nav:
                        startActivity(new Intent(getApplicationContext(),homepage.class));
                        finish();
                        overridePendingTransition(0,0);
                        break;

                }

            }
        });

            Intent b = getIntent();
            String token_no = b.getStringExtra(EXTRA_NUMBER);
            token.setText(token_no);
    }
}