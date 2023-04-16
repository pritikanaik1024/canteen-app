package com.example.delights;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class admin_edit_menu extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    Admin_menu_Adapter Admin_menu_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_menu);


        recyclerView = findViewById(R.id.rv_2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Food"), MainModel.class)
                        .build();

        Admin_menu_adapter = new Admin_menu_Adapter (options);
        recyclerView.setAdapter(Admin_menu_adapter);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),admin_add.class));
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        Admin_menu_adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Admin_menu_adapter.stopListening();
    }
}
