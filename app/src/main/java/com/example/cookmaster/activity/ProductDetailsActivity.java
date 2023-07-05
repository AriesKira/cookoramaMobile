package com.example.cookmaster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cookmaster.R;

public class ProductDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        getActionBar().hide();


    }
}