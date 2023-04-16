package com.example.delights;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class admin_add extends AppCompatActivity {
    EditText add_food,add_price,add_url;
    Button add,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add);

        add_food = findViewById(R.id.food_add);
        add_price = findViewById(R.id.price_add);
        add_url = findViewById(R.id.uri_add);
        add = findViewById(R.id.admin_add);
        back = findViewById(R.id.back_add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
                clear();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void insertData()
    {
        Map<String,Object> map = new HashMap<>();
        map.put("name",add_food.getText().toString());
        map.put("price",add_price.getText().toString());
        map.put("uri",add_url.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Food").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(admin_add.this, "Item has been Added!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(admin_add.this, "Error! while Adding", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clear()
    {
        add_food.setText("");
        add_price.setText("");
        add_url.setText("");
    }
}