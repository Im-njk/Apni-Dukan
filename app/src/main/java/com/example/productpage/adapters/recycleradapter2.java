package com.example.productpage.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.productpage.R;
import com.example.productpage.activities.HomeActivity;
import com.example.productpage.activities.SplashScreen;
import com.example.productpage.activities.productactivity;
import com.example.productpage.model.product;

import java.io.Serializable;
import java.util.List;

public class recycleradapter2  extends RecyclerView.Adapter<recycleradapter2 .ViewHolder>{
    Context context;
    List<product> product;
    public recycleradapter2(Context context, List<com.example.productpage.model.product> product) {
        this.context = context;
        this.product = product;
    }



    @NonNull
    @Override
    public recycleradapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler3, parent, false);
        return new recycleradapter2.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recycleradapter2.ViewHolder holder, int position) {
        product p2= product.get(position);
        String sss=p2.getTitle();
        holder.name.setText(sss);
        holder.price.setText(p2.getPrice());
        Glide.with(context).load(p2.getImage()).into(holder.photo);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id =p2.getId();
                Intent intent =new Intent(context,productactivity.class);
                intent.putExtra("id",id);
                Pair[] pairs=new Pair[3];
                pairs[0]=new Pair<>(holder.photo,"p_image");
                pairs[1]=new Pair<>(holder.name,"p_name");
                pairs[2]=new Pair<>(holder.price,"p_price");
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)context, pairs);
                    ActivityCompat.startActivity(context, intent,options.toBundle());
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return product.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
         ImageView photo;
         TextView name,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo=itemView.findViewById(R.id.proimg);
            name=itemView.findViewById(R.id.protxt);
            price=itemView.findViewById(R.id.pricexml);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
