package com.example.delights;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder> {

    private List<relation> relations;
    private TextView rateview;

    // Constructor
    public myadapter(List<relation> relations, TextView rateview) {
        this.relations = relations;
        this.rateview = rateview;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_recycler, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, final int position) {
        relation currentRelation = relations.get(position);

        holder.recpname.setText(currentRelation.getFood_name());
        holder.recpprice.setText(currentRelation.getSum());
        holder.recpqnt.setText(currentRelation.getQuantity());

        holder.delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relation_database db = Room.databaseBuilder(holder.recpname.getContext(),
                        relation_database.class, "Food_4").allowMainThreadQueries().build();
                relationDAO relationdao = db.relationDao();
                relationdao.deletebyId(currentRelation.getFid());
                relations.remove(position);
                notifyItemRemoved(position);
                updateprice();
            }
        });
    }

    @Override
    public int getItemCount() {
        return relations.size();
    }

    public static class myviewholder extends RecyclerView.ViewHolder {
        TextView recpname, recpprice, recpqnt;
        ImageButton delbtn;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            recpname = itemView.findViewById(R.id.recpname);
            recpprice = itemView.findViewById(R.id.recpprice);
            recpqnt = itemView.findViewById(R.id.recpqnt);
            delbtn = itemView.findViewById(R.id.recpdelete);
        }
    }

    private void updateprice() {
        int sum = 0;

        if (relations.size() == 0) {
            rateview.setText(String.valueOf(0));
        } else {
            for (relation rel : relations) {
                sum += (Integer.parseInt(rel.getSum()) * Integer.parseInt(rel.getQuantity()));
            }
            rateview.setText(String.valueOf(sum));
        }
    }
}
