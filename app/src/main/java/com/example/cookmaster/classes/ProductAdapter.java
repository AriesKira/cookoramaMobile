package com.example.cookmaster.classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmaster.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductsViewHolder>{

    private List<ShopItem> productsList;

    public ProductAdapter(List<ShopItem> productsList) {
        if (productsList != null && !productsList.isEmpty()) {
            this.productsList = productsList;
        }

    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.shop_item_layout, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        ShopItem product = productsList.get(position);
        holder.diplayShopProduct(product);
    }

    @Override
    public int getItemCount() {
        if (productsList == null) {
            return 0;
        }else {
            return productsList.size();
        }
    }
}
