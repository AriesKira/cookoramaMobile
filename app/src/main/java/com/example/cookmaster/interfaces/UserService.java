package com.example.cookmaster.interfaces;

import com.example.cookmaster.classes.LoginRequest;
import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface UserService {
    @GET("user/{string}")
    Call<JsonObject> getUser(@Path("string") String userInfo);

    @GET("user/{id}")
    Call<JsonObject> getUser(@Path("id") int id);

    @Headers("Content-Type: application/json")
    @POST("user/connect")
    Call<JsonObject> connectUser(@Body LoginRequest loginRequest);

    @POST("user/connectToken")
    Call<JsonObject> connectUserToken(@Header("Authorization") String token);

}

