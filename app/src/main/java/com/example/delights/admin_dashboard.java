package com.example.delights;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class admin_view_orders extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private TextView transactionDetailsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_orders);

        transactionDetailsTextView = findViewById(R.id.transaction_details);

        databaseReference = FirebaseDatabase.getInstance().getReference("Transactions");

        // Example: Listening to a specific transaction update
        String transactionId = "someTransactionId"; // Replace with the actual transaction ID you want to monitor
        String userId = "someUserId"; // Replace with the actual user ID associated with the transaction

        databaseReference.child(transactionId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String transactionDetails = dataSnapshot.getValue(String.class);
                    transactionDetailsTextView.setText(transactionDetails);
                    // Optionally update the transaction status here
                    updateTransactionStatus(transactionId, userId, "Completed"); // Example status update
                } else {
                    transactionDetailsTextView.setText("Transaction not found.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(admin_view_orders.this, "Error loading transaction details.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateTransactionStatus(String transactionId, String userId, String newStatus) {
        Map<String, Object> statusUpdate = new HashMap<>();
        statusUpdate.put("status", newStatus);

        // Update the transaction status in the Transactions node
        databaseReference.child(transactionId)
                .updateChildren(statusUpdate)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(admin_view_orders.this, "Transaction status updated.", Toast.LENGTH_SHORT).show();
                    updateUserTransactionStatus(userId, transactionId, newStatus);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(admin_view_orders.this, "Error updating transaction status.", Toast.LENGTH_SHORT).show();
                });
    }

    private void updateUserTransactionStatus(String userId, String transactionId, String newStatus) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("transactions").child(transactionId);

        Map<String, Object> userUpdate = new HashMap<>();
        userUpdate.put("status", newStatus);

        userRef.updateChildren(userUpdate)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(admin_view_orders.this, "User transaction status updated.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(admin_view_orders.this, "Error updating user transaction status.", Toast.LENGTH_SHORT).show();
                });
    }
}
