package com.example.cookmaster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cookmaster.R;
import com.example.cookmaster.Utils.ImageHandler;
import com.example.cookmaster.classes.ShopItem;

public class ProductDetailsActivity extends AppCompatActivity {

    private ImageView productImage;
    private Button buyButton,returnButton;
    private TextView productPrice, productDescription, productName;
    private ShopItem product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        getSupportActionBar().hide();

        productDescription = findViewById(R.id.productDescription);
        productImage = findViewById(R.id.productImage);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        buyButton = findViewById(R.id.buyButton);
        returnButton = findViewById(R.id.returnButton);

        Intent intent = getIntent();
        product = (ShopItem) intent.getSerializableExtra("product");

        productDescription.setText(product.getDescription());
        productName.setText(product.getName());
        productPrice.setText(product.getPrice());
        ImageHandler.setImage(productImage, product.getImage());

        returnButton.setOnClickListener(v -> {
            finish();
        });

    }
}