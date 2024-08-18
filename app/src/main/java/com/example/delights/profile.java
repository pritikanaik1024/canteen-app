package com.example.delights;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class profile extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Field declarations
        EditText profileName = findViewById(R.id.profile_name);
        EditText profileNumber = findViewById(R.id.profile_number);
        AutoCompleteTextView branchAutoComplete = findViewById(R.id.profile_branch);
        TextView emailTextView = findViewById(R.id.profile_email);
        Button saveButton = findViewById(R.id.profile_button);
        EditText profileGRNumber = findViewById(R.id.profile_gr_number);

        // Set autofill for branch
        String[] branches = new String[] {
                "AI-DS_FE", "AI-DS_SE", "AI-DS_TE", "AI-DS_BE",
                "COMPS_FE", "COMPS_SE", "COMPS_TE", "COMPS_BE",
                "CIVIL_FE", "CIVIL_SE", "CIVIL_TE", "CIVIL_BE",
                "MECH_FE", "MECH_SE", "MECH_TE", "MECH_BE",
                "CHEM_FE", "CHEM_SE", "CHEM_TE", "CHEM_BE",
                "CIVIL-INFRA_FE", "CIVIL-INFRA_SE", "CIVIL-INFRA_TE", "CIVIL-INFRA_BE",
                "IT_FE", "IT_SE", "IT_TE", "IT_BE",
                "ELEC_FE", "ELEC_SE", "ELEC_TE", "ELEC_BE"
        };
        branchAutoComplete.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, branches));

        // Set email from Register activity
        Intent intent = getIntent();
        String profileEmail = intent.getStringExtra(Register.EXTRA_NAME);
        emailTextView.setText(profileEmail);

        // Save button click listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = profileName.getText().toString();
                String number = profileNumber.getText().toString();
                String gr = profileGRNumber.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(number) || TextUtils.isEmpty(gr)) {
                    Toast.makeText(profile.this, "Please provide valid details", Toast.LENGTH_SHORT).show();
                } else {
                    Intent a = new Intent(profile.this, Fetch.class);
                    startActivity(a);
                    finish();
                    DocumentCreate();
                    Toast.makeText(profile.this, "Your profile has been created successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Firebase Firestore link of profile
    private void DocumentCreate() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String uid = auth.getUid();
        EditText profileName = findViewById(R.id.profile_name);
        EditText profileNumber = findViewById(R.id.profile_number);
        AutoCompleteTextView branchAutoComplete = findViewById(R.id.profile_branch);
        EditText profileGRNumber = findViewById(R.id.profile_gr_number);
        String name = profileName.getText().toString();
        String number = profileNumber.getText().toString();
        String gr = profileGRNumber.getText().toString();
        Intent intent = getIntent();
        String branch = branchAutoComplete.getText().toString();

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("Roll Number", number);
        map.put("GR Number", gr);
        map.put("email", intent.getStringExtra(Register.EXTRA_NAME));
        map.put("Branch", branch);

        Map<String, String> adminMap = new HashMap<>();
        adminMap.put("is_admin", "0");

        db.collection("Users").document(uid).set(adminMap);
        db.collection("Users").document(uid).collection(gr)
                .document(intent.getStringExtra(Register.EXTRA_NAME)).set(map);
    }
}
