package com.example.productpage.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.productpage.Dbhandler;
import com.example.productpage.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.victor.loading.book.BookLoading;

public class MainActivity extends AppCompatActivity {
    TextView signuptext,error;
    Button loginbtn;
    EditText number,password;
    public static int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        error=findViewById(R.id.errormsg);
        signuptext=findViewById(R.id.signupxml);
        signuptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Signup.class);
                startActivity(intent);
            }
        });
        loginbtn=findViewById(R.id.loginbtnxml);
        number=findViewById(R.id.numberlogin);
        password=findViewById(R.id.passwordlogin);
        Dbhandler db= new Dbhandler(this);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqllitelogin(db);
               // firebaselogin();


            }
            
        });
        
    }

    private void firebaselogin() {
        error.setText("");
        String phone= number.getText().toString();
        String passworduser=password.getText().toString();

        DatabaseReference reference= FirebaseDatabase.getInstance("https://productpage-3267c-default-rtdb.firebaseio.com/").getReference("User");
        Query checkuser=reference.orderByChild("number").equalTo(phone);
        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    String sPassword=dataSnapshot.child(phone).child("password").getValue(String.class);
                    if(sPassword.equals(passworduser)){
                        String namedb=dataSnapshot.child(phone).child("name").getValue(String.class);
                        String phonedb=dataSnapshot.child(phone).child("number").getValue(String.class);
                        Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                        intent.putExtra("name",namedb);
                        intent.putExtra("number",phonedb);
                        startActivity(intent);

                    }
                    else{
                        error.setText("Wrong Credentials");

                    }
                }else{
                    error.setText("No Such User Exists");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sqllitelogin( Dbhandler db) {

                id=db.checklogin(number.getText().toString(),password.getText().toString());
                if(id!=-1){
                    Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }else{
                    error.setText("Invalid number or Password");
                }
    }
}