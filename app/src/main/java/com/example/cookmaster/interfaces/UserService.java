package com.example.cookmaster.interfaces;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface UserService {
    @GET("user/{string}")
    Call<JsonObject> getUser(@Path("string") String userInfo);

    @GET("user/{id}")
    Call<JsonObject> getUser(@Path("id") int id);
}

