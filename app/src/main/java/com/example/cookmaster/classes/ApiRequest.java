package com.example.cookmaster.classes;

import android.util.Log;

import com.example.cookmaster.interfaces.ProductService;
import com.example.cookmaster.interfaces.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.*;
import java.net.*;
import java.util.*;


public class ApiRequest {
    private static final String BASE_URL = "http://169.254.204.77/api/";
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

    public void getProducts() {
        Call<JsonObject> call = productService.getProducts();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                if (jsonObject != null) {
                    Log.d("API Response", "Response code: " + response.code());
                    Log.d("API Response", "Response body: " + jsonObject.toString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("API Failure", t.getMessage());
            }

        });

    }

}
