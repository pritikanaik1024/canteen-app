package com.example.delights;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

public class homepage extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    User_Menu_Adapter user_menu_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.cart_nav:
                        startActivity(new Intent(getApplicationContext(), pay.class));
                        finish();
                        overridePendingTransition(1, 1);
                        break;
                    case R.id.token_nav:
                        startActivity(new Intent(getApplicationContext(), token.class));
                        finish();
                        overridePendingTransition(1, 1);
                        break;

                    case R.id.profile_nav:
                        startActivity(new Intent(getApplicationContext(), profile_home.class));
                        finish();
                        overridePendingTransition(1, 1);
                        break;

                    case R.id.home_nav:
                        break;

                }

            }
        });



        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Food"), MainModel.class)
                        .build();

        user_menu_adapter = new User_Menu_Adapter(options);
        recyclerView.setAdapter(user_menu_adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        user_menu_adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        user_menu_adapter.stopListening();
    }
}