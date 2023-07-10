package com.example.cookmaster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookmaster.R;
import com.example.cookmaster.Utils.ImageHandler;
import com.example.cookmaster.classes.ShopItem;

public class ProductDetailsActivity extends AppCompatActivity {

    private ImageView productImage;
    private Button buyButton,returnButton;
    private TextView productPrice, productDescription, productName;
    private ShopItem product;

    private static final String BASE_URL = "https://cookorama.fr/boutique/produit/";
    private String noBrowserError = String.valueOf(R.string.no_browser_error);
    private String url;
    private Uri webpage;


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
        this.url = BASE_URL + product.getId();
        this.webpage = Uri.parse(url);

        productDescription.setText(product.getDescription());
        productName.setText(product.getName());
        productPrice.setText(product.getPrice());
        ImageHandler.setImage(productImage, product.getImage());

        returnButton.setOnClickListener(v -> {
            finish();
        });

        buyButton.setOnClickListener(v -> {
            Intent buyIntent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(buyIntent);
            } else {
                Toast.makeText(this, noBrowserError + url, Toast.LENGTH_SHORT).show();
            }
        });

    }
}