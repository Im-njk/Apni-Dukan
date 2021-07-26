package com.example.productpage.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productpage.ApiInterface;
import com.example.productpage.Dbhandler;
import com.example.productpage.activities.WishlistActivity;
import com.example.productpage.model.newuserinfo;
import com.example.productpage.R;
import com.example.productpage.adapters.Recycleradapter;
import com.example.productpage.adapters.SliderAdapter;
import com.example.productpage.apis;
import com.example.productpage.model.Category;
import com.example.productpage.model.SliderItem;
import com.example.productpage.model.newuserinfo;
import com.example.productpage.model.product;
import com.example.productpage.adapters.recycleradapter2;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    //RecyclerView
    RecyclerView cat, topproduct, suggestedrecycle;
    Recycleradapter Radapter;
    recycleradapter2 Radapter3, Radapter2;

    //textviews
    TextView nameh, numberh;
    String hname;
   public static String hnumber;

    //Lists of porduct
    ArrayList<Category> c1 = new ArrayList<>();
    List<product> topproductl = new ArrayList<>();
    List<product> topproductl2 = new ArrayList<>();
    product p1;

    //Slider
    List<SliderItem> items = new ArrayList<>();
    SliderAdapter adapter = new SliderAdapter(this, items);

    //NavigationDrawer
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageButton navbtn;

    //other
    ImageView cart,search_icon;
    TextView cartcount;
    Dbhandler db=new Dbhandler(HomeActivity.this);
    newuserinfo user;
    int id;
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        cartcount.setText(String.valueOf(db.get_cartcount()));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        cartcount=findViewById(R.id.cart_count);
        search_icon=findViewById(R.id.search_icon);
//        search_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i =new Intent(HomeActivity.this,SearchActivity.class);
//                startActivity(i);
//            }
//        });

        p1=new product();

        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);

         user = db.userdatabyid(id);



        navbtn = findViewById(R.id.navbtn);
        cart = findViewById(R.id.cartbtn);
        cat = findViewById(R.id.catrecycler);
        cat.setHasFixedSize(true);
        cat.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        topproduct = findViewById(R.id.topproductrecycler);
        topproduct.setHasFixedSize(true);
        topproduct.setLayoutManager(new GridLayoutManager(this, 2));
        suggestedrecycle = findViewById(R.id.suggestedrecycler);
        suggestedrecycle.setHasFixedSize(true);
        suggestedrecycle.setLayoutManager(new GridLayoutManager(this, 2));


        Category c = new Category("Jewelery", R.drawable.grocery);
        c1.add(c);
        Category c5 = new Category("Electronics", R.drawable.electronics);
        c1.add(c5);
        Category c2 = new Category("Men's Fashion", R.drawable.fashion);
        c1.add(c2);
        Category c4 = new Category("Women's Fashion", R.drawable.cosmetics);
        c1.add(c4);
        Radapter = new Recycleradapter(this, c1);
        cat.setAdapter(Radapter);




        product p1 = new product();
        db.addproduct();
        topproductl=db.fetchproductbycat();
        topproductl2=db.fetchproductbycat();
        Radapter2 = new recycleradapter2(HomeActivity.this, topproductl2);
        suggestedrecycle.setAdapter(Radapter2);
        Radapter3 = new recycleradapter2(HomeActivity.this, topproductl);
        topproduct.setAdapter(Radapter3);







        SliderItem item = new SliderItem(R.drawable.sliderdemo);
        SliderItem items1 = new SliderItem(R.drawable.sliderdemo2);
        SliderItem items2 = new SliderItem(R.drawable.sliderdemo3);
        items.add(item);
        items.add(items1);
        items.add(items2);

        SliderView sliderView = findViewById(R.id.slider);
        sliderView.setSliderAdapter(new SliderAdapter(this, items));
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();
        sliderView.setSliderAdapter(adapter);

        navigationdrawer();



        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HomeActivity.this, cartactivity.class);
                intent1.putExtra("number",hnumber);
                startActivity(intent1);
            }
        });
    }

    private void navigationdrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toogle);
        toogle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nv);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.navMyOrders:
                        break;
                    case R.id.navMywishlist:  Intent intent = new Intent(HomeActivity.this, WishlistActivity.class);
                             startActivity(intent);
                             break;
                }
            return false;}
        });
        View headerView = navigationView.getHeaderView(0);
        nameh = headerView.findViewById(R.id.headerusername);
        numberh = headerView.findViewById(R.id.headernumber);
        numberh.setText(user.getnumber());
        nameh.setText(user.getName());
        navbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        }
        );
    }

//
//    private void fetchproductbyid(int idint) {
//        product p1 = new product();
//        String id = Integer.toString(idint);
//        DatabaseReference reference = FirebaseDatabase.getInstance("https://productpage-3267c-default-rtdb.firebaseio.com/").getReference("Products");
//        Query checkproduct = reference.child(id);
//        checkproduct.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                boolean f=dataSnapshot.exists();
//                if (f) {
//                    String titles=dataSnapshot.child("Title").getValue(String.class);
//                    p1.setTitle(titles);
//                    String des=dataSnapshot.child("Discription").getValue(String.class);
//                    p1.setDescription(des);
//                    String img=dataSnapshot.child("Imgurl").getValue(String.class);
//                    p1.setImage(img);
//                    String cat=dataSnapshot.child("Category").getValue(String.class);
//                    p1.setCategory(cat);
//                    String pr=dataSnapshot.child("price").getValue(String.class);
//                    p1.setPrice(pr);
////                    int id=dataSnapshot.child("id").getValue(Integer.class);
////                    p1.setId(id);
//
//
//                    topproductl2.add(p1);
//                    topproductl2.add(p1);
//                    topproductl2.add(p1);
//                    topproductl2.add(p1);
//                    Radapter2 = new recycleradapter2(HomeActivity.this, topproductl2,);
//                    suggestedrecycle.setAdapter(Radapter2);
//
//                    topproductl.add(p1);
//                    topproductl.add(p1);
//                    topproductl.add(p1);
//                    topproductl.add(p1);
//                    Radapter3 = new recycleradapter2(HomeActivity.this, topproductl);
//                    topproduct.setAdapter(Radapter3);
//
//                } else {
//                    Toast.makeText(HomeActivity.this, "no item found", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }
}
