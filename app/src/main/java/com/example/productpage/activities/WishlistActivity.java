package com.example.productpage.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.productpage.Dbhandler;
import com.example.productpage.R;
import com.example.productpage.adapters.recycleradapter2;
import com.example.productpage.model.product;

import java.util.ArrayList;
import java.util.List;

public class WishlistActivity extends AppCompatActivity {
    RecyclerView r1;
    recycleradapter2 recycleradapter;
    List<product> list_product = new ArrayList<>();
    ImageView backbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        backbtn=findViewById(R.id.wish_backbtn);
        r1=findViewById(R.id.wish_recyclercart);
        r1.setHasFixedSize(true);
        r1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        Dbhandler db=new Dbhandler(this);
        list_product=db.fetchall_wishlist();
        recycleradapter=new recycleradapter2(WishlistActivity.this,list_product);
        recycleradapter.notifyDataSetChanged();
        r1.setAdapter(recycleradapter);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}