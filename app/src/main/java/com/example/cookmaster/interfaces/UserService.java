package com.example.cookmaster.interfaces;

import com.example.cookmaster.classes.CheckPwd;
import com.example.cookmaster.classes.LoginRequest;
import com.example.cookmaster.classes.UpdatePwd;
import com.example.cookmaster.classes.UserUpdatedInfos;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface UserService {
    @GET("user/{string}")
    Call<JsonObject> getUserInfos(@Path("string") String userInfo);

    @GET("user/id/{id}")
    Call<JsonObject> getUserInfos(@Path("id") int id, @Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @POST("user/connect")
    Call<JsonObject> connectUser(@Body LoginRequest loginRequest);

    @POST("user/connectToken")
    Call<JsonObject> connectUserToken(@Header("Authorization") String token);

    @POST("user/checkPassword")
    @Headers("Content-Type: application/json")
    Call<JsonObject> checkPassword(@Header("Authorization") String token,@Body CheckPwd pwd);

    @POST("user/update")
    @Headers("Content-Type: application/json")
    Call<JsonObject> updateUser(@Header("Authorization") String token,@Body UserUpdatedInfos userUpdatedInfos );

    @POST("user/updatePassword")
    @Headers("Content-Type: application/json")
    Call<JsonObject> updateUserPassword(@Header("Authorization") String token,@Body UpdatePwd pwd);
}

