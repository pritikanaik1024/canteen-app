package com.example.delights;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_Menu_Adapter extends FirebaseRecyclerAdapter<MainModel,User_Menu_Adapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     */
    public User_Menu_Adapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model) {
        holder.name.setText(model.getName());
        holder.price.setText(model.getPrice());

        Glide.with(holder.img.getContext())
                .load(model.getUri())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark)
                .into(holder.img);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_user_recycler,parent,false);
        return new myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView name;
        TextView price;
        Button add_cart;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            add_cart = itemView.findViewById(R.id.add_cart);

        }
    }
}
