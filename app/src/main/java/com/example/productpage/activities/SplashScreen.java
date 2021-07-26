package com.example.productpage.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.productpage.R;

public class SplashScreen extends AppCompatActivity {
  ImageView imageView;
  TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imageView=findViewById(R.id.splash_icon);
        t1=findViewById(R.id.splash_name);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                Pair[] pairs =new Pair[2];
                pairs[0]=new Pair<>(imageView, "logo_icon");
                pairs[1]=new Pair<>(t1, "logo_name");
               if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(SplashScreen.this, pairs);
                ActivityCompat.startActivity(SplashScreen.this, i,options.toBundle());
            }
        }}, 3000);



    }
}