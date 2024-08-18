package com.example.delights;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_Menu_Adapter extends FirebaseRecyclerAdapter<MainModel, User_Menu_Adapter.myViewHolder> {

    public User_Menu_Adapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    private int Quantity = 1;
    private int totalprice = 1;

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

        holder.add_cart.setOnClickListener(v -> {
            final DialogPlus dialogPlus = DialogPlus.newDialog(holder.name.getContext())
                    .setContentHolder(new ViewHolder(R.layout.add_to_cart_popup))
                    .setExpanded(true, 1100)
                    .create();

            relation_database db = Room.databaseBuilder(v.getContext(),
                    relation_database.class, "Food_4").allowMainThreadQueries().build();
            View view = dialogPlus.getHolderView();
            TextView Food_name = view.findViewById(R.id.cart_food);
            TextView Price = view.findViewById(R.id.cart_price);
            TextView Sum = view.findViewById(R.id.sum_food);
            ImageButton add = view.findViewById(R.id.add_cart);
            ImageButton sub = view.findViewById(R.id.sub_cart);
            TextView count = view.findViewById(R.id.count);
            Button final_add = view.findViewById(R.id.add_final);
            CircleImageView cart_img = view.findViewById(R.id.cart_img);

            Food_name.setText(model.getName());
            Price.setText(model.getPrice());
            Sum.setText(model.getPrice());

            Glide.with(cart_img.getContext())
                    .load(model.getUri())
                    .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                    .circleCrop()
                    .error(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark)
                    .into(cart_img);

            add.setOnClickListener(v1 -> {
                if (Quantity < 10) {
                    Quantity++;
                    count.setText(String.valueOf(Quantity));
                    totalprice = Integer.parseInt(model.getPrice()) * Quantity;
                    Sum.setText(String.valueOf(totalprice));
                }
            });

            sub.setOnClickListener(v1 -> {
                if (Quantity > 1) {
                    Quantity--;
                    count.setText(String.valueOf(Quantity));
                    totalprice = Integer.parseInt(model.getPrice()) * Quantity;
                    Sum.setText(String.valueOf(totalprice));
                }
            });

            final_add.setOnClickListener(v1 -> {
                relationDAO relationdao = db.relationDao();
                relationdao.insertall(new relation(Food_name.getText().toString(), Sum.getText().toString(), String.valueOf(Quantity)));
                Quantity = 1;
                count.setText(String.valueOf(Quantity));
                Toast.makeText(holder.name.getContext(), "Item added to Cart", Toast.LENGTH_SHORT).show();
            });

            dialogPlus.show();
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_user_recycler, parent, false);
        return new myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder {
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

    public static class MainModel {
        private String name;
        private String price;
        private String uri;

        public MainModel() { }

        public MainModel(String name, String price, String uri) {
            this.name = name;
            this.price = price;
            this.uri = uri;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }
}
