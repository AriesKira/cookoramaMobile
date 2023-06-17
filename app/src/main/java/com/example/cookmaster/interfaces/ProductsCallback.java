package com.example.cookmaster.interfaces;

import com.example.cookmaster.classes.ShopItem;

import java.util.List;

public interface ProductsCallback {
    void onProductsReceived(List<ShopItem> products);
    void onProductsFailure(String errorMessage);
}

