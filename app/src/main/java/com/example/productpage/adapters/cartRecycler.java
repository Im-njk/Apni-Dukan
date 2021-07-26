package com.example.productpage.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.productpage.Dbhandler;
import com.example.productpage.R;
import com.example.productpage.activities.productactivity;
import com.example.productpage.model.product;

import java.util.List;

public class cartRecycler extends RecyclerView.Adapter<cartRecycler .ViewHolder>{
    Context context;
    List<com.example.productpage.model.product> product1;
    TextView t1;

    public void setC1(cartRecycler c1) {
        this.c1 = c1;
    }

    cartRecycler c1;
    public cartRecycler(Context context, List<com.example.productpage.model.product> product,TextView t1) {
        this.context = context;
        this.product1 = product;
        this.t1=t1;
    }



    @NonNull
    @Override
    public cartRecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartrecycler, parent, false);
        return new cartRecycler.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cartRecycler.ViewHolder holder, int position) {
        product p2= product1.get(position);
        holder.name.setText(p2.getTitle());
        holder.price.setText(p2.getPrice().toString());
        Glide.with(context).load(p2.getImage()).into(holder.photo);
        Dbhandler db=new Dbhandler(context);
        holder.quantity.setText(String.valueOf(db.getintitem("QUANTITY","CART",p2.getId())));
        holder.plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dbhandler db=new Dbhandler(context);
                Toast.makeText(context, "+", Toast.LENGTH_SHORT).show();
                int b=db.changequantity("+",p2.getId(),Integer.parseInt(p2.getPrice()));
                holder.quantity.setText(String.valueOf(b));
                int a= db.getintitem("SUM(PRICE)","CART");
                t1.setText(String.valueOf(a));
                c1.notifyDataSetChanged();
            }
        });
        holder.minusbtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Dbhandler db=new Dbhandler(context);
              if(db.getintitem("COUNT(QUANTITY)","CART")>0){
              int b=db.changequantity("-",p2.getId(),Integer.parseInt(p2.getPrice()));
              holder.quantity.setText(String.valueOf(b));
              Toast.makeText(context, "-", Toast.LENGTH_SHORT).show();
              int a= db.getintitem("SUM(PRICE)","CART");
              t1.setText(String.valueOf(a));
              if(b==0)
                  db.removeitem(p2.getId(),"CART");
                  product1.remove(position);}
              c1.notifyDataSetChanged();
          }

      });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.removeitem(p2.getId(),"CART");
                product1.remove(position);
                c1.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return product1.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photo,minusbtn,plusbtn;
        TextView name,price,quantity,remove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo= itemView.findViewById(R.id.porimgcart);
            name=itemView.findViewById(R.id.protitlecart);
            price=itemView.findViewById(R.id.propricecart1);
            plusbtn=itemView.findViewById(R.id.plusbtn);
            minusbtn=itemView.findViewById(R.id.minusbtn);
            quantity=itemView.findViewById(R.id.quantity);
            remove=itemView.findViewById(R.id.removecart);
        }
    }
}
