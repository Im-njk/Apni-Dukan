package com.example.productpage.activities;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.productpage.Dbhandler;
import com.example.productpage.R;
import com.example.productpage.apis;
import com.example.productpage.model.product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class productactivity extends AppCompatActivity {
    ImageView proimg, wishlist,backbtn;
    TextView title,price,discreption;
    Button addtocart;
    product p1=new product();

    FirebaseDatabase rootnode;
    DatabaseReference reference,reference1,reference2;
    boolean flag ;
    boolean wish;
    Dbhandler db =new Dbhandler(productactivity.this);
    int id;
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        flag= db.checkitemincart(id);
        if(!flag){
            addtocart.setText("Add to cart");
        }else
            addtocart.setText("Go to cart");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productactivity);



        proimg=findViewById(R.id.productimg);
        title=findViewById(R.id.productname);
        price=findViewById(R.id.proprice);
        discreption=findViewById(R.id.discretpionxml);
        wishlist=findViewById(R.id.wishlistbtnxml);
        backbtn=findViewById(R.id.backbtn);
        addtocart=findViewById(R.id.addtocartxml);

        Intent intent=getIntent();
        //p1=Gproductpassactivity(intent);
         id=intent.getIntExtra("id",0);

        p1=db.getproductbyid(id);

        boolean itemcart=db.checkitemincart(id);
        if(itemcart)
            addtocart.setText("Go to cart");



        Glide.with(this).load(p1.getImage()).into(proimg);
        title.setText(p1.getTitle());
        price.setText(price.getText()+p1.getPrice());
        discreption.setText(p1.getDescription());
        wishlist.bringToFront();
        backbtn.bringToFront();





        wish=db.checkitemin_wishlist(id);
        if(wish){
            wishlist.setImageResource(R.drawable.wishlistseleted);
            wish=false;}
        else{
            wishlist.setImageResource(R.drawable.wishlistunselected);
            wish=true;
        }

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                       flag= db.checkitemincart(id);


               if(!flag){
                   Toast.makeText(productactivity.this, "true", Toast.LENGTH_SHORT).show();
                   db.additemtocart(id);
                addtocart.setText("Go to cart");}
               else
                   Toast.makeText(productactivity.this, "false", Toast.LENGTH_SHORT).show();
               Intent intent1=new Intent(productactivity.this,cartactivity.class);
               startActivity(intent1);
            }
        });



        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wish){
                wishlist.setImageResource(R.drawable.wishlistseleted);
                db.additemto_wishlist(id);
                wish=false;}
                else{
                    wishlist.setImageResource(R.drawable.wishlistunselected);
                    db.removeitem(id,"WISHLIST");
                    wish=true;
                }
            }
        });

//        addtocart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(addtoc){
//                Intent intent1 = new Intent(productactivity.this,cartactivity.class);
//                startActivity(intent1);}
//                else{
//                    rootnode=FirebaseDatabase.getInstance(apis.firebaseUrl);
//                    reference1.child("User").child(HomeActivity.hnumber).child("cart").child(String.valueOf(p1.getId()));
//                    reference1.setValue(p1.getId());
//                    reference2.child("User").child(HomeActivity.hnumber);
//                    Query inccart=reference2.child("cartitem");
//                    inccart.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            String s=dataSnapshot.getValue(String.class);
//                            int is=Integer.parseInt(s);
//                            is++;
//                            reference2.child("cartitem").setValue(String.valueOf(is));
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//            }
//        });
    }
   // public static void Sproductpassactivity(Context context, Class productactivity, product p1){
//        Intent intent = new Intent(context, productactivity.class);
//                intent.putExtra("title",p1.getTitle());
//                intent.putExtra("des",p1.getDescription());
//                intent.putExtra("img",p1.getImage());
//                intent.putExtra("rate",p1.getPrice());
//                intent.putExtra("id",p1.getId());
//                intent.putExtra("cat",p1.getCategory());
//        context.startActivity(intent);
//    }
//    public static product Gproductpassactivity(Intent intent){
//
//        String titles=intent.getStringExtra("title");
//        String description=intent.getStringExtra("des");
//        String img=intent.getStringExtra("img");
//        String prices=intent.getStringExtra("rate" );
//        String cat=intent.getStringExtra("cat");
//        int id=intent.getIntExtra("id",0);
//        product p1=new product();
//        p1.setId(id);
//        p1.setCategory(cat);
//        p1.setImage(img);
//        p1.setDescription(description);
//        p1.setTitle(titles);
//        p1.setPrice(prices);
//        return p1;
//    }
}