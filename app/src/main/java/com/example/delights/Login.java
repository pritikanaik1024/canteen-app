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

public class Login extends AppCompatActivity {
    // Firebase link
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // field declarations
        EditText email = findViewById(R.id.login_email);
        EditText password = findViewById(R.id.login_password);
        Button login_2 = findViewById(R.id.login_button);

        login_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_check = email.getText().toString();
                String password_check = password.getText().toString();

                if(TextUtils.isEmpty(email_check)){
                    Toast.makeText(Login.this, "Please enter a valid email used for registration", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(password_check)){
                    Toast.makeText(Login.this, "Please enter a valid password used during registration", Toast.LENGTH_SHORT).show();
                }
                else{
                    login(email_check,password_check);
                }
            }
        });
    }
    public void login(String email,String password){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(Login.this, "successfully logged in", Toast.LENGTH_SHORT).show();
                    Intent a = new Intent(Login.this,Homescreen.class);
                    startActivity(a);
                    finish();
                }
                else
                {
                    Toast.makeText(Login.this, "Login unsuccessful , Please register yourself to login!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}