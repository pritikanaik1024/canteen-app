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

    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // All declarations
        EditText profile_Name = findViewById(R.id.profile_name);
        EditText profile_number = findViewById(R.id.profile_number);
        AutoCompleteTextView atv = findViewById(R.id.profile_branch);
        TextView email = findViewById(R.id.profile_email);
        Button save = findViewById(R.id.profile_button);
        EditText profile_gr = findViewById(R.id.profile_gr_number);

       // set autofill for branch
        String [] Branch = new String[]
                {"AI-DS_FE","AI-DS_SE","AI-DS_TE","AI-DS_BE",
                        "COMPS_FE","COMPS_SE","COMPS_TE","COMPS_BE",
                        "CIVIL_FE","CIVIL_SE","CIVIL_TE","CIVIL_BE",
                        "MECH_FE","MECH_SE","MECH_TE","MECH_BE",
                        "CHEM_FE","CHEM_SE","CHEM_TE","CHEM_BE", "CIVIL-INFRA_FE","CIVIL-INFRA_SE","CIVIL-INFRA_TE","CIVIL-INFRA_BE", "IT_FE","IT_SE","IT_TE","IT_BE", "ELEC_FE","ELEC_SE","ELEC_TE","ELEC_BE" };

        atv.setAdapter(new ArrayAdapter<String>(profile.this, android.R.layout.simple_list_item_1,Branch));
        // set email from register to this activity without changes using textview

        Intent intent = getIntent();
        String profile_email = intent.getStringExtra(Register.EXTRA_NAME);
        email.setText(profile_email);

        //Conditions to check if the name and number is empty or not
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String name = profile_Name.getText().toString();
                String number= profile_number.getText().toString();
                String gr = profile_gr.getText().toString();


                if(TextUtils.isEmpty(name)||TextUtils.isEmpty(number)||TextUtils.isEmpty(gr))
                {
                    Toast.makeText(profile.this, "Please provide valid details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent a = new Intent(profile.this, Fetch.class);
                    startActivity(a);
                    finish();
                    DocumentCreate();
                    Toast.makeText(profile.this, "Your profile have been created successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // FirebaseFirestore link of profile
    public void DocumentCreate() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String uid = auth.getUid();
        EditText profile_Name = findViewById(R.id.profile_name);
        EditText profile_number = findViewById(R.id.profile_number);
        AutoCompleteTextView atv = findViewById(R.id.profile_branch);
        EditText profile_gr_number = findViewById(R.id.profile_gr_number);
        String name = profile_Name.getText().toString();
        String number= profile_number.getText().toString();
        String gr = profile_gr_number.getText().toString();
        Intent intent = getIntent();
        String branch = atv.getText().toString();

        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("Roll Number",number);
        map.put("GR Number",gr);
        map.put("email",intent.getStringExtra(Register.EXTRA_NAME));
        map.put("Branch",branch);

        Map<String, String> admin_map = new HashMap<>();
        admin_map.put("is_admin","0");



        db.collection("Users").document(auth.getUid()).set(admin_map);
        db.collection("Users").document(auth.getUid()).collection(gr)
                .document(intent.getStringExtra(Register.EXTRA_NAME)).set(map);


//        FirebaseFirestore.getInstance().collection("Profile Information").add(map);
    }
}