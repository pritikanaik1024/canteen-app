package com.example.delights;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;


import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class  Admin_menu_Adapter extends FirebaseRecyclerAdapter<MainModel,Admin_menu_Adapter.myViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     */

    public Admin_menu_Adapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull MainModel model) {

        holder.admin_name.setText(model.getName());
        holder.admin_price.setText(model.getPrice());
        Glide.with(holder.admin_img.getContext())
                .load(model.getUri())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark)
                .into(holder.admin_img);

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.admin_img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.admin_update_popup))
                        .setExpanded(true,1250)
                        .create();


                View view = dialogPlus.getHolderView();

                EditText Food = view.findViewById(R.id.food_update);
                EditText Price = view.findViewById(R.id.price_update);
                EditText Url = view.findViewById(R.id.uri_update);

                Button update = view.findViewById(R.id.admin_update);

                Food.setText(model.getName());
                Price.setText(model.getPrice());
                Url.setText(model.getUri());

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("name",Food.getText().toString());
                        map.put("price",Price.getText().toString());
                        map.put("uri",Url.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Food")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.admin_name.getContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.admin_name.getContext(), "Update Failure!", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });

                 dialogPlus.show();

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.admin_name.getContext());
                builder.setTitle("Are you sure for Deletion?");
                builder.setMessage("Deleted data cannot be retrieved");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseDatabase.getInstance().getReference().child("Food")
                                .child(getRef(position).getKey()).removeValue();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.admin_name.getContext(), "Item not Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });


    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_admin_recycler,parent,false);
        return new myViewHolder(view);
    }


    static class myViewHolder extends RecyclerView.ViewHolder{

        CircleImageView admin_img;
        TextView admin_name;
        TextView admin_price;
        Button update;
        Button delete;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            admin_img = itemView.findViewById(R.id.admin_img);
            admin_name = itemView.findViewById(R.id.admin_name);
            admin_price = itemView.findViewById(R.id.admin_price);
            update = itemView.findViewById(R.id.update);
            delete = itemView.findViewById(R.id.delete);

        }
    }

    public static class MainModel_user {



        String name,price,uri;


        MainModel_user()
        {


        }

        public MainModel_user(String name, String price, String uri) {
            this.name = name;
            this.price = price;
            this.uri = uri;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getName() {
            return name;
        }

        public String getPrice() {
            return price;
        }

        public String getUri() {
            return uri;
        }


    }
}
