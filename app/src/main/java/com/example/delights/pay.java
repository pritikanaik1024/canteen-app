package com.example.delights;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class pay extends AppCompatActivity implements PaymentResultListener {

    private RecyclerView recyclerView;
    private TextView rateview;
    private Button pay_btn;
    private Checkout checkout;
    private BottomNavigationView bottomNavigationView;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        Checkout.preload(getApplicationContext());

        pay_btn = findViewById(R.id.pay_btn);
        recyclerView = findViewById(R.id.rv_3);
        rateview = findViewById(R.id.rateview);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.cart_nav);

        getroomdata();

        bottomNavigationView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.cart_nav:
                        break;
                    case R.id.token_nav:
                        startActivity(new Intent(getApplicationContext(), token.class));
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.profile_nav:
                        startActivity(new Intent(getApplicationContext(), profile_home.class));
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.home_nav:
                        startActivity(new Intent(getApplicationContext(), homepage.class));
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                }
            }
        });

        pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startpayment();
            }
        });
    }

    private void getroomdata() {
        relation_database db = Room.databaseBuilder(getApplicationContext(),
                relation_database.class, "Food_4").allowMainThreadQueries().build();
        relationDAO relationdao = db.relationDao();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<relation> relations = relationdao.getAll_relations();

        myadapter adapter = new myadapter(relations, rateview);
        recyclerView.setAdapter(adapter);

        int sum = 0;
        if (relations.size() > 0) {
            for (relation rel : relations) {
                sum += (Integer.parseInt(rel.getSum()) * Integer.parseInt(rel.getQuantity()));
            }
        }
        rateview.setText(String.valueOf(sum));
    }

    private void startpayment() {
        int value = Integer.parseInt(rateview.getText().toString());
        value *= 100; // Razorpay expects amount in paise
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_Ww6qbnSXWQ3pNZ");
        checkout.setImage(R.drawable.logo);
        final Activity activity = this;
        try {
            JSONObject options = new JSONObject();
            options.put("name", "delights");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", value);
            options.put("prefill.email", "ai.50.zaurez.timmapuri@gmail.com");
            options.put("prefill.contact", "9619127270");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore fdb = FirebaseFirestore.getInstance();

        relation_database db = Room.databaseBuilder(getApplicationContext(),
                relation_database.class, "Food_4").allowMainThreadQueries().build();
        relationDAO relationdao = db.relationDao();
        List<relation> relations = relationdao.getAll_relations();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("orders");

        int i = 0;
        for (relation rel : relations) {
            databaseReference.child(s).child(String.valueOf(++i)).child("name").setValue(rel.getFood_name());
            databaseReference.child(s).child(String.valueOf(i)).child("Quantity").setValue(rel.getQuantity());
        }

        relationdao.deleteall();
        getroomdata();

        Map<String, Object> token_map = new HashMap<>();
        token_map.put("Token", s);
        fdb.collection("Orders").document(auth.getUid()).set(token_map);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Oops! your payment is unsuccessful", Toast.LENGTH_SHORT).show();
    }
}
