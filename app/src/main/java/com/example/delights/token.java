package com.example.delights;
// NOT IN USE WAS USED FOR TOKEN GENERATION
//import static com.example.delights.pay.EXTRA_NUMBER;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class token extends AppCompatActivity {

    TextView token ;
    BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        token = findViewById(R.id.token_txt);


        bottomNavigationView.setSelectedItemId(R.id.token_nav);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.cart_nav:
                        startActivity(new Intent(getApplicationContext(),pay.class));
                        finish();
                        overridePendingTransition(0,0);
                        break;
                    case R.id.token_nav:
                        break;

                    case R.id.profile_nav:
                        startActivity(new Intent(getApplicationContext(),profile_home.class));
                        finish();
                        overridePendingTransition(0,0);
                        break;

                    case R.id.home_nav:
                        startActivity(new Intent(getApplicationContext(),homepage.class));
                        finish();
                        overridePendingTransition(0,0);
                        break;

                }

            }
        });


        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore fdb = FirebaseFirestore.getInstance();
        Task<DocumentSnapshot> document =  fdb.collection("Orders").document(auth.getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        token.setText(documentSnapshot.getString("Token"));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        // NOT IN USE WAS USED FOR TOKEN GENERATION
//            Intent b = getIntent();
//            String token_no = b.getStringExtra(EXTRA_NUMBER);
//            token.setText(token_no);
    }
}