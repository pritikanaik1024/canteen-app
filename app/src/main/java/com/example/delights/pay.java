package com.example.delights;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class pay extends AppCompatActivity implements PaymentResultListener {

    TextView pay_id ;
    Button pay_btn ;
    private Checkout checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        Checkout.preload(getApplicationContext());

        pay_id = findViewById(R.id.pay_id);
        pay_btn = findViewById(R.id.pay_btn);

        pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startpayment();

            }
        });

    }

    private void startpayment() {

            Checkout checkout = new Checkout();
            checkout.setKeyID("rzp_test_Ww6qbnSXWQ3pNZ");
            checkout.setImage(R.drawable.delights_splash);
            final Activity activity = this;
            try {
                JSONObject options = new JSONObject();

                options.put("name", "delights");
                options.put("description", "Reference No. #123456");
                options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
               // options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
                options.put("theme.color", "#3399cc");
                options.put("currency", "INR");
                options.put("amount", "50000");//pass amount in currency subunits
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
        pay_id.setText("payment is sucessful"+s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        pay_id.setText("payment is unsucessful"+s);
    }
}
