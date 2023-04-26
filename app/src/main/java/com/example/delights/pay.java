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

    // NOT IN USE WAS USED FOR TOKEN GENERATION
//    public static final String EXTRA_NUMBER = "com.example.delights.extra.NUMBER";


    RecyclerView recyclerView;
    TextView rateview;
    Button pay_btn ;

    Checkout checkout;
    BottomNavigationView bottomNavigationView;

    DatabaseReference databaseReference;



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
                switch (item.getItemId())
                {
                    case R.id.cart_nav:
                        break;

                    case R.id.token_nav:
                        startActivity(new Intent(getApplicationContext(),token.class));
                        finish();
                        overridePendingTransition(0,0);
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

        myadapter adapter = new myadapter (relations,rateview);
        recyclerView.setAdapter(adapter);

        int sum = 0, i;
        if (relations.size()==0){
            sum = 0;
            rateview.setText(String.valueOf(0));
        }
        else {

            for (i = 0; i < relations.size(); i++) {

                sum = sum + (Integer.parseInt(relations.get(i).getSum()) * Integer.parseInt(relations.get(i).getQuantity()));
                rateview.setText(String.valueOf(sum));
            }
        }

    }

    private void startpayment() {

            int value = Integer.parseInt(String.valueOf(rateview.getText()));
            value = value * 100;
            Checkout checkout = new Checkout();
            checkout.setKeyID("rzp_test_Ww6qbnSXWQ3pNZ");
            checkout.setImage(R.drawable.logo);
            final Activity activity = this;
            try {
                JSONObject options = new JSONObject();

                options.put("name", "delights");
                options.put("description", "Reference No. #123456");
                options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
               // options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
                options.put("theme.color", "#3399cc");
                options.put("currency", "INR");
                options.put("amount", value);//pass amount in currency subunits
                options.put("prefill.email", "ai.50.zaurez.timmapuri@gmail.com");
                options.put("prefill.contact","9619127270");
                JSONObject retryObj = new JSONObject();
                retryObj.put("enabled", true);
                retryObj.put("max_count", 4);
                options.put("retry", retryObj);

                checkout.open(activity, options);

            } catch(Exception e) {
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
        while(i<relations.size()){
            String name = relations.get(i).food_name;
            String Quantity = relations.get(i).quantity;
            i++;
            databaseReference.child(s).child(String.valueOf(i)).child("name").setValue(name);
            databaseReference.child(s).child(String.valueOf(i)).child("Quantity").setValue(Quantity);
        }

        relationdao.deleteall();
        getroomdata();

        Map<String,Object> token_map = new HashMap<>();
        token_map.put("Token",s);
         fdb.collection("Orders").document(auth.getUid()).set(token_map);


//        String Token = s;
//        Intent a = new Intent();
//        a.putExtra(EXTRA_NUMBER,Token);


     // NOT IN USE WAS USED FOR TOKEN GENERATION
//        int token = IncrementingNumber.getNextNumber();
//        String token_number = Integer.toString(token);
//        Intent a = new Intent();
//        a.putExtra(EXTRA_NUMBER,token_number);

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Oops! your payment is unsuccessful", Toast.LENGTH_SHORT).show();
    }

// NOT IN USE WAS USED FOR TOKEN GENERATION
//    public static class IncrementingNumber {
//
//        private static int number = 0;
//
//        public static int getNextNumber() {
//            number = number % 100 + 1;
//            return number;
//        }
//
//    }
}




