package com.example.cookmaster.classes;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cookmaster.R;
import com.example.cookmaster.activity.ProductDetailsActivity;

public class ProductsViewHolder extends RecyclerView.ViewHolder{
    private final TextView name;
    private final TextView price;
    private final ImageView image;

    private final LinearLayout productLayout;

    private ShopItem product;

    public ProductsViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.shopProductName);
        price = itemView.findViewById(R.id.shopProductPrice);
        image = itemView.findViewById(R.id.shopProductImage);
        productLayout = itemView.findViewById(R.id.shopProductHolder);
    }

    public void diplayShopProduct(ShopItem product) {
        this.product = product;
        if (product.getName().length() > 15) {
            name.setText(product.getName().substring(0, 12) + "...");
        }else {
            name.setText(product.getName());
        }
        price.setText(product.getPrice());
        image.setImageResource(R.drawable.ic_launcher_background);
        productLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent details =  new Intent(v.getContext(), ProductDetailsActivity.class);
               details.putExtra("product", product);
               v.getContext().startActivity(details);
            }
        });
    }

    public void setImageUrl(String imageUrl) {
        Glide.with(itemView.getContext()).load(imageUrl).into(image);
    }
}
