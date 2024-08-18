package com.example.delights;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class admin_edit_menu extends AppCompatActivity {
    EditText add_food, add_price, add_url;
    Button add, back;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add);

        add_food = findViewById(R.id.food_add);
        add_price = findViewById(R.id.price_add);
        add_url = findViewById(R.id.uri_add);
        add = findViewById(R.id.admin_add);
        back = findViewById(R.id.back_add);

        databaseReference = FirebaseDatabase.getInstance().getReference();

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

    private void insertData() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", add_food.getText().toString());
        map.put("price", add_price.getText().toString());
        map.put("uri", add_url.getText().toString());

        String foodKey = databaseReference.child("Food").push().getKey();

        databaseReference.child("Food").child(foodKey)
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(admin_edit_menu.this, "Item has been Added!", Toast.LENGTH_SHORT).show();
                        // Optionally process a transaction after adding the item
                        processTransaction(foodKey, "USER123"); // Replace "USER123" with actual user ID
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(admin_edit_menu.this, "Error! while Adding", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clear() {
        add_food.setText("");
        add_price.setText("");
        add_url.setText("");
    }

    private void processTransaction(String transactionId, String userId) {
        Map<String, Object> transactionMap = new HashMap<>();
        transactionMap.put("transactionId", transactionId);
        transactionMap.put("status", "Pending");

        databaseReference.child("Transactions").child(transactionId)
                .setValue(transactionMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        updateUserTransactionStatus(userId, transactionId, "Pending");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(admin_edit_menu.this, "Error processing transaction", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateUserTransactionStatus(String userId, String transactionId, String status) {
        Map<String, Object> userUpdate = new HashMap<>();
        userUpdate.put("transactionId", transactionId);
        userUpdate.put("status", status);

        databaseReference.child("Users").child(userId).child("transactions").child(transactionId)
                .updateChildren(userUpdate)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(admin_edit_menu.this, "User transaction status updated", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(admin_edit_menu.this, "Error updating user transaction status", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
