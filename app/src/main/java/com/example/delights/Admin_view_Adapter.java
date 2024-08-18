package com.example.delights;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Admin_view_Adapter extends FirebaseRecyclerAdapter<MainModel_View, Admin_view_Adapter.myViewHolder> {

    public Admin_view_Adapter(@NonNull FirebaseRecyclerOptions<MainModel_View> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel_View model) {

        holder.admin_quantity_view.setText(model.getQuantity());
        holder.admin_name_view.setText(model.getName());
        holder.pay_name.setText(model.getPayName());
        holder.pay_token.setText(model.getPayToken());

        // Mark as Ready
        holder.view_ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOrderStatus(position, "Ready", model.getUserId());
            }
        });

        // Mark as Delivered
        holder.view_delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOrderStatus(position, "Delivered", model.getUserId());
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_admin_recycler, parent, false);
        return new myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder {

        TextView pay_name, pay_token, admin_name_view, admin_quantity_view;
        Button view_ready, view_delivered;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            pay_name = itemView.findViewById(R.id.pay_name);
            pay_token = itemView.findViewById(R.id.pay_token);
            admin_name_view = itemView.findViewById(R.id.admin_name_view);
            admin_quantity_view = itemView.findViewById(R.id.admin_Quantity_view);
            view_delivered = itemView.findViewById(R.id.view_delivered);
            view_ready = itemView.findViewById(R.id.view_ready);
        }
    }

    private void updateOrderStatus(int position, String status, String userId) {
        String orderKey = getRef(position).getKey();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        Map<String, Object> statusUpdate = new HashMap<>();
        statusUpdate.put("status", status);

        // Update the order status in the database
        databaseReference.child("Orders").child(orderKey)
                .updateChildren(statusUpdate)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(admin_name_view.getContext(), "Order marked as " + status, Toast.LENGTH_SHORT).show();
                        updateUserTransactionStatus(userId, orderKey, status);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(admin_name_view.getContext(), "Failed to update status", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateUserTransactionStatus(String userId, String orderKey, String status) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        Map<String, Object> userUpdate = new HashMap<>();
        userUpdate.put("status", status);

        databaseReference.child("Users").child(userId).child("orders").child(orderKey)
                .updateChildren(userUpdate)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(admin_name_view.getContext(), "User transaction status updated", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(admin_name_view.getContext(), "Failed to update user transaction status", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
