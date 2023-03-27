package com.example.delights;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class admin_dashboard extends AppCompatActivity {

    ImageButton edit_menu;
    ImageButton orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        edit_menu = findViewById(R.id.edit_menu);
        orders = findViewById(R.id.orders);


        edit_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(admin_dashboard.this,admin_edit_menu.class);
                startActivity(a);
            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent b = new Intent(admin_dashboard.this,admin_view_orders.class);
                startActivity(b);
                finish();
            }
        });
    }
}