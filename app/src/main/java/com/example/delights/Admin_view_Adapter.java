package com.example.delights;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class Admin_view_Adapter extends FirebaseRecyclerAdapter <MainModel_View , Admin_view_Adapter.myViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Admin_view_Adapter(@NonNull FirebaseRecyclerOptions<MainModel_View> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel_View model) {


        holder.admin_quantity_view.setText(model.getQuantity());
        holder.admin_name_view.setText(model.getName());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_admin_recycler,parent,false);
        return new Admin_view_Adapter.myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder {

        TextView pay_name,pay_token,admin_name_view,admin_quantity_view;
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
}
