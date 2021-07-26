package com.example.productpage.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.SearchManager;
import android.os.Bundle;

import com.example.productpage.R;

public class SearchActivity extends AppCompatActivity {
    androidx.appcompat.widget.SearchView searchView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

    }
}