package com.example.cookmaster.classes;

import android.util.Log;

import com.example.cookmaster.interfaces.ProductService;
import com.example.cookmaster.interfaces.ProductsCallback;
import com.example.cookmaster.interfaces.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.*;
import java.net.*;
import java.util.*;


public class ApiRequest {
    private static final String BASE_URL = "https://cookorama.fr/api/";
    private UserService userService;
    private ProductService productService;
    public ApiRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);
        productService = retrofit.create(ProductService.class);

    }

    public void getUser(String userInfo) {
        Call<JsonObject> call = userService.getUser(userInfo);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (jsonObject != null) {
                        Log.d("API Response", "Response code: " + response.code());
                        Log.d("API Response", "Response body: " + jsonObject.toString());
                    }
                } else {
                    Log.d("API Error", "Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("API Failure", t.getMessage());
            }

        });

    }

    public void getUser(int id) {
        Call<JsonObject> call = userService.getUser(id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (jsonObject != null) {
                        Log.d("API Response", "Response code: " + response.code());
                        Log.d("API Response", "Response body: " + jsonObject.toString());
                    }
                } else {
                    Log.d("API Error", "Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("API Failure", t.getMessage());
            }

        });

    }
    public void getProducts(ProductsCallback callback) {
        Call<JsonObject> call = productService.getProducts();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                if (jsonObject != null) {
                    Log.d("API Response", "Response code: " + response.code());
                    Log.d("API Response", "Response body: " + jsonObject.toString());

                    List<ShopItem> shopItems = new ArrayList<>();
                    JsonArray products = jsonObject.getAsJsonArray("products");
                    for (JsonElement product : products) {
                        JsonObject productObject = product.getAsJsonObject();
                        String name = productObject.get("name").getAsString();
                        String description = productObject.get("description").getAsString();
                        String price = productObject.get("price").getAsString();
                        String quantity = productObject.get("quantity").getAsString();
                        int id = productObject.get("id").getAsInt();
                        String image = productObject.get("image").getAsString();
                        ShopItem shopItem = new ShopItem(name, description, price, quantity, id, image);
                        shopItems.add(shopItem);
                    }
                    callback.onProductsReceived(shopItems);
                } else {
                    callback.onProductsFailure("Response body is null");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onProductsFailure(t.getMessage());
            }
        });
    }


}
