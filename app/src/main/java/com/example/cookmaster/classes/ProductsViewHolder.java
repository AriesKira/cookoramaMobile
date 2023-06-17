package com.example.cookmaster.classes;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cookmaster.R;

public class ProductsViewHolder extends RecyclerView.ViewHolder{
    private final TextView name;
    private final TextView price;
    private final ImageView image;

    private ShopItem product;

    public ProductsViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.shopProductName);
        price = itemView.findViewById(R.id.shopProductPrice);
        image = itemView.findViewById(R.id.shopProductImage);
    }

    public void diplayShopProduct(ShopItem product) {
        this.product = product;
        name.setText(product.getName());
        price.setText(product.getPrice());
        image.setImageResource(R.drawable.ic_launcher_background);
    }

    public void setImageUrl(String imageUrl) {
        Glide.with(itemView.getContext()).load(imageUrl).into(image);
    }
}
