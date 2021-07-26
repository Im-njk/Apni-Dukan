package com.example.productpage.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productpage.Dbhandler;
import com.example.productpage.R;
import com.example.productpage.adapters.cartRecycler;
import com.example.productpage.model.product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class cartactivity extends AppCompatActivity {
    RecyclerView recycler;
    cartRecycler r1;
    product p1;
    List<product> listincart =new ArrayList<>();
    List<Integer> productids=new ArrayList<>();
    boolean b1=true;
    ImageView backbtn;
    TextView totalcartprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartactivity);
        backbtn=findViewById(R.id.backbtn);
        totalcartprice=findViewById(R.id.totalcartprice);
        recycler = findViewById(R.id.recyclercart);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
       p1=new product();
        Dbhandler db=new Dbhandler(cartactivity.this);
        listincart=db.fetchallcart();
        r1=new cartRecycler(this,listincart,totalcartprice);
        r1.setC1(r1);
        recycler.setAdapter(r1);
        //Toast.makeText(this, String.valueOf(a), Toast.LENGTH_SHORT).show();
        int a= db.getintitem("SUM(PRICE)","CART");
        totalcartprice.setText(String.valueOf(a));

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent1=new Intent(cartactivity.this,productactivity.class);
//                startActivity(intent1);
                onBackPressed();
            }
        });
//        listincart.add(p1);
//        listincart.add(p1);
//        listincart.add(p1);
//        listincart.add(p1);

//        r1=new cartRecycler(this,listincart);
//        recycler.setAdapter(r1);

//        DatabaseReference refernce= FirebaseDatabase.getInstance("https://productpage-3267c-default-rtdb.firebaseio.com/").getReference("User");
//        Query getnumber=refernce.child(HomeActivity.hnumber);
//        getnumber.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    //Toast.makeText(cartactivity.this, dataSnapshot.child("cartitem").getValue(String.class), Toast.LENGTH_SHORT).show();
//                    int totalcart=Integer.parseInt(dataSnapshot.child("cartitem").getValue(String.class));
////                   productids=
//                           getnumlist(refernce,totalcart);
//                  //  Toast.makeText(cartactivity.this, String.valueOf(b1), Toast.LENGTH_SHORT).show();
//
//
//                }else
//                    Toast.makeText(cartactivity.this, "no 1", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }
//
//    private void getnumlist(DatabaseReference refernce, int totalcart) {
//        Query listofid=refernce.child(HomeActivity.hnumber).child("cart");
//        List<Integer> list= new ArrayList<>();
//        listofid.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()) {
//                    // Toast.makeText(cartactivity.this, String.valueOf(dataSnapshot.child("1").getValue(Integer.class)), Toast.LENGTH_SHORT).show();
//                    for (int i = 1; i <= totalcart; i++) {
//                        String ss = String.valueOf(i);
//                        //                 Toast.makeText(cartactivity.this, String.valueOf(dataSnapshot.child(ss).getValue(Integer.class)), Toast.LENGTH_SHORT).show();
//                        int id = dataSnapshot.child(ss).getValue(Integer.class);
//                        //Toast.makeText(cartactivity.this, String.valueOf(id), Toast.LENGTH_SHORT).show();
//                        list.add(id);
//
//                    }
//                    getitemsforcart(list);
//                }else
//                    Toast.makeText(cartactivity.this, "no 2", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        b1=false;
//        //return list;
//    }
//
//    private void getitemsforcart(List<Integer> list) {
//        DatabaseReference reference=FirebaseDatabase.getInstance("https://productpage-3267c-default-rtdb.firebaseio.com/").getReference("Products");
//        for (int i = 0; i < list.size()+1; i++){
//            if (i < list.size()) {
//                String xx = String.valueOf(list.get(i));
//                Query getitem = reference.child(xx);
//                getitem.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.exists()) {
//                            // Toast.makeText(cartactivity.this,  dataSnapshot.child("Title").getValue(String.class), Toast.LENGTH_SHORT).show();
//                            String titles = dataSnapshot.child("Title").getValue(String.class);
//                            p1.setTitle(titles);
//                            // Toast.makeText(cartactivity.this, p1.getTitle(), Toast.LENGTH_SHORT).show();
//                            String des = dataSnapshot.child("Discription").getValue(String.class);
//                            p1.setDescription(des);
//                            String img = dataSnapshot.child("Imgurl").getValue(String.class);
//                            p1.setImage(img);
//                            String cat = dataSnapshot.child("Category").getValue(String.class);
//                            p1.setCategory(cat);
//                            String pr = dataSnapshot.child("price").getValue(String.class);
//                            p1.setPrice(pr);
//                            listincart.add(p1);
//                            Toast.makeText(cartactivity.this, listincart.get(0).getTitle(), Toast.LENGTH_SHORT).show();
//                        } else
//                            Toast.makeText(cartactivity.this, "no 3", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }else{
//                r1 = new cartRecycler(cartactivity.this, listincart);
//                recycler.setAdapter(r1);
//               // Toast.makeText(cartactivity.this, listincart.get(0).getTitle(), Toast.LENGTH_SHORT).show();
//            }
//        }
//
//
   }
}