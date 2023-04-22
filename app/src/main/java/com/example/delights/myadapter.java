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

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder>
{
    List<relation> relations;
    TextView rateview;


    public myadapter (List<relation> relations ,TextView rateview){

        this.relations = relations;
        this.rateview = rateview;
    }


    @NonNull
    @Override
    public myadapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_recycler,parent,false);
      return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myadapter.myviewholder holder, final int position) {

        holder.recpname.setText(relations.get(position).getFood_name());
        holder.recpprice.setText(relations.get(position).getSum());
        holder.recpqnt.setText(relations.get(position).getQuantity());

        holder.delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relation_database db = Room.databaseBuilder(holder.recpname.getContext(),
                        relation_database.class, "Food_4").allowMainThreadQueries().build();
                relationDAO relationdao = db.relationDao();
                relationdao.deletebyId(relations.get(position).getFid());
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

        TextView recpname,recpprice,recpqnt;
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

        int sum = 0, i;
        if (relations.size()==0){
            sum = 0;
            rateview.setText(String.valueOf(0));
        }
        else {

            for (i = 0; i < relations.size(); i++) {

                sum = sum + (Integer.parseInt(relations.get(i).getSum()) * Integer.parseInt(relations.get(i).getQuantity()));
                rateview.setText(String.valueOf(sum));
            }
        }

        }

}
