package com.example.delights;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        AutoCompleteTextView atv = findViewById(R.id.profile_branch);
        String [] Branch = new String[] {"AI-DS","COMPS","CIVIL","MECH","CHEM","CIVIL-INFRA","IT","ELEC" };
        atv.setAdapter(new ArrayAdapter<String>(profile.this, android.R.layout.simple_list_item_1,Branch));

        Button save = findViewById(R.id.profilr_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(profile.this,Fetch.class);
                startActivity(a);
                finish();
                Toast.makeText(profile.this, "You have been Successfully been Registered", Toast.LENGTH_SHORT).show();

            }
        });


    }
}