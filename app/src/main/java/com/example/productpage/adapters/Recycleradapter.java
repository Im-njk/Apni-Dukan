package com.example.productpage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.productpage.R;
import com.example.productpage.model.Category;

import java.util.List;

public class Recycleradapter extends RecyclerView.Adapter<Recycleradapter.ViewHolder>  {
    Context centext;
    List<Category> category;

    public Recycleradapter(Context centext, List<Category> category) {
        this.centext = centext;
        this.category = category;
    }


    @NonNull
    @Override
    public Recycleradapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categorylayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Recycleradapter.ViewHolder holder, int position) {
      Category category1=category.get(position);
      holder.categoryname.setText(category1.getC_name());
      holder.Photo.setImageResource(category1.getImg());
    }

    @Override
    public int getItemCount() {
        return category.size();
    }
    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView categoryname;
        ImageView Photo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryname=itemView.findViewById(R.id.Categorynamexml);
            Photo=itemView.findViewById(R.id.catimg);
            Photo.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }



    }
}
