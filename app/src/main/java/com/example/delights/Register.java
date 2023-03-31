package com.example.delights;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    // Firebase declaration
    FirebaseAuth auth = FirebaseAuth.getInstance();
    // creation of a final String to pass into next activity
    public static final String EXTRA_NAME = "com.example.delights.extra.NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //All declarations
        Button register_2 = findViewById(R.id.register_2);
        EditText email = findViewById(R.id.register_email);
        EditText password = findViewById(R.id.register_password);

        register_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Field declarations and conversion to string
                String email_check = email.getText().toString();
                String password_check = password.getText().toString();
                //checking conditions for email field
                if (TextUtils.isEmpty(email_check ))
                {
                    Toast.makeText(Register.this, "Please enter valid email address", Toast.LENGTH_SHORT).show();
                }
                //checking conditions for password field
                if(TextUtils.isEmpty(password_check) ){
                    Toast.makeText(Register.this, "Please enter valid password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // call for regis function
                    regis(email_check,password_check);
                }
            }
        });
    }
    // Regis function declaration
    public void regis(String email,String password)
    {
        // call for creation of user name and password
      auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
              if(task.isSuccessful()){
                  // message to be shown on successful creation
                  Toast.makeText(Register.this, "you have been successfully registered", Toast.LENGTH_SHORT).show();
                  Intent intent = new Intent(Register.this,profile.class);
                  //field to be passed in next activity
                  intent.putExtra(EXTRA_NAME,email);

                  startActivity(intent);
                  finish();
              }
              else
              {
                  // message to be shown on unsuccessful creation
                  Toast.makeText(Register.this, "Please enter a valid email and password", Toast.LENGTH_SHORT).show();
              }
          }
      });
    }

    public static class MainModel {
    }
}