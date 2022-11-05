package com.example.delights;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login_2 = findViewById(R.id.login_2);

        login_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Login.this,Homescreen.class);
                startActivity(a);
                Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}