package com.example.delights;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth = FirebaseAuth.getInstance();
 Handler h = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         h.postDelayed(new Runnable() {
           @Override
          public void run() {
         Intent a = new Intent(MainActivity.this,Fetch.class);
           startActivity(a);
           finish();
            }
          },2000);
    }

    // When app is ready and is used for login
    // issue that the user has already deleted from the database yet it is allowing to login
    // check on that

   // @Override
   // protected void onStart() {
   //     super.onStart();
   //     FirebaseUser authCurrentUser = auth.getCurrentUser();
   //     if(authCurrentUser!=null){
  //          h.postDelayed(new Runnable() {
  //              @Override
   //             public void run() {
   //                 Intent a = new Intent(MainActivity.this,Homescreen.class);
  //                  startActivity(a);
  //                  finish();
   //             }
  //          },2000);

   //     }
    //    else{
   //         h.postDelayed(new Runnable() {
   //             @Override
   //             public void run() {
   //                 Intent a = new Intent(MainActivity.this,Fetch.class);
   //                 startActivity(a);
   //                 finish();
   //             }
    //        },2000);
  //      }

   // }

}