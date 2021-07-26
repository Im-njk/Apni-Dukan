package com.example.productpage.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productpage.Dbhandler;
import com.example.productpage.R;
import com.example.productpage.model.newuserinfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class Signup extends AppCompatActivity {
   TextView logintxt,errortxt;
   EditText name,number,password,cpassword;
   newuserinfo info;
   Button Signupbtn;


   //FireBase
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        logintxt=findViewById(R.id.loginxml);
        errortxt=findViewById(R.id.errortxtxml);
        Dbhandler db=new Dbhandler(this);
        Signupbtn=findViewById(R.id.signupbtnxml);
        logintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Signup.this, MainActivity.class);
                startActivity(intent);
            }
        });

        name=findViewById(R.id.namexml);
        number=findViewById(R.id.numberxml);
        password=findViewById(R.id.passwordxml);
        cpassword=findViewById(R.id.confirmpassxml);
        Signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.getText().toString().isEmpty()) {
                    errortxt.setText("Please enter Name");
                } else if (number.getText().toString().isEmpty()) {
                    errortxt.setText("Please enter number");
                } else if (password.getText().toString().isEmpty()) {
                    errortxt.setText("Please enter Password");
                } else if (cpassword.getText().toString().isEmpty()) {
                    errortxt.setText("Please confirm your Password");
                } else if (!password.getText().toString().equals(cpassword.getText().toString())) {
                    errortxt.setText("Password does not match with Confirm Password");
                } else if (password.getText().toString().length() < 6) {
                    errortxt.setText("Please enter Password");
                } else {
                    info = new newuserinfo(name.getText().toString(), number.getText().toString(), password.getText().toString());
                   sqlitesignup(db);
                    //firebasesignup(info);
//                    Intent intent =new Intent(Signup.this,verifyotp.class);
//                    intent.putExtra("number",number.getText().toString());
//                    startActivity(intent);

                }
            }
        });



    }

    private void firebasesignup( newuserinfo info) {
        rootnode = FirebaseDatabase.getInstance("https://productpage-3267c-default-rtdb.firebaseio.com/");
        String phone=info.getnumber();
        reference = rootnode.getReference("User");
        Query checkuser = reference.orderByChild("number").equalTo(phone);
        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    Toast.makeText(Signup.this, "Username already exists", Toast.LENGTH_SHORT).show();
                else {
                    reference = rootnode.getReference("User").child(info.getnumber());
                    reference.setValue(info);
                    Toast.makeText(Signup.this, "You are registered", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void sqlitesignup(Dbhandler db) {
        boolean pass;
        pass=db.adduser(info);
        if(pass){
            errortxt.setText("Hurray! you are Registered. \nClick here to Login");
            errortxt.setTextColor(getResources().getColor(R.color.black));
            errortxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Signup.this,MainActivity.class);
                    startActivity(intent);
                }
            });
           }
        else
            errortxt.setText("You are already a User");
    }
}