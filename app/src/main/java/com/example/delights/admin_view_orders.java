package com.example.delights;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class admin_view_orders extends AppCompatActivity {

    RecyclerView recyclerView;
    Admin_view_Adapter admin_view_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_orders);

        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.rv_4);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Configure FirebaseRecyclerOptions to use the Orders data
        FirebaseRecyclerOptions<MainModel_View> options =
                new FirebaseRecyclerOptions.Builder<MainModel_View>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Orders"), MainModel_View.class)
                        .build();

        // Initialize the Admin_view_Adapter with the options
        admin_view_adapter = new Admin_view_Adapter(options);
        recyclerView.setAdapter(admin_view_adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Start listening to Firebase data when the activity starts
        admin_view_adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Stop listening to Firebase data when the activity stops
        admin_view_adapter.stopListening();
    }
}
