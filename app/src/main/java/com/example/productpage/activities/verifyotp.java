package com.example.productpage.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productpage.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class verifyotp extends AppCompatActivity {
    Button verifybtn;
    TextView number;
    EditText otp;
    String phone,verificationcodebysystem;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyotp);
        verifybtn=findViewById(R.id.verifybtn);
        number=findViewById(R.id.numbertxt);
        otp=findViewById(R.id.otpxml);
        mAuth = FirebaseAuth.getInstance(FirebaseApp.getInstance("https://productpage-3267c-default-rtdb.firebaseio.com/"));
        Intent intent=getIntent();
        phone=intent.getStringExtra("number");
        number.setText(number.getText().toString()+phone);
        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
