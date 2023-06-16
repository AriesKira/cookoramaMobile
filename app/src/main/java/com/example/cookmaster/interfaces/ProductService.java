package com.example.cookmaster.interfaces;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductService {
    @GET("products")
    Call<JsonObject> getProducts();

}
