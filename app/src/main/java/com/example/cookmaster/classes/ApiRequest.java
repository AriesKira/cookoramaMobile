package com.example.cookmaster.classes;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.cookmaster.interfaces.OnValidatePasswordCallback;
import com.example.cookmaster.interfaces.ProductService;
import com.example.cookmaster.interfaces.ProductsCallback;
import com.example.cookmaster.interfaces.LoginUserCallback;
import com.example.cookmaster.interfaces.UpdateUserCallback;
import com.example.cookmaster.interfaces.UserCallback;
import com.example.cookmaster.interfaces.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
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

    public void connectUser(String email, String password, LoginUserCallback callback) {
        LoginRequest loginRequest = new LoginRequest(email, password);


        Call<JsonObject> call = userService.connectUser(loginRequest);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                if (jsonObject != null) {
                    Log.d("API Response", "Response code: " + response.code());
                    Log.d("API Response", "Response body: " + jsonObject.toString());
                    callback.onConnectionSuccess(jsonObject.get("token").getAsString(),jsonObject.get("id").getAsInt());
                } else {
                    Log.d("API Error", "Response code: " + response.code());
                    JsonObject errorObject = null;
                    String errorMessage = null;
                    try {
                        String errorBodyString = response.errorBody().string();
                        Log.d("API Error", "Response body: " + errorBodyString);
                        errorObject = new Gson().fromJson(errorBodyString, JsonObject.class);
                        errorMessage = errorObject.get("message").getAsString();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    callback.onConnectionFailure(errorMessage);
                }
            }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("API Failure", t.getMessage());
                callback.onConnectionFailure(t.getMessage());
            }

        });

    }

    public void connectUserToken(String token, LoginUserCallback callback) {
        Call<JsonObject> call = userService.connectUserToken(token);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                if (jsonObject != null) {
                    Log.d("API Response", "Response code: " + response.code());
                    Log.d("API Response", "Response body: " + jsonObject.toString());
                    callback.onConnectionSuccess(jsonObject.get("token").getAsString(),jsonObject.get("id").getAsInt());
                } else {
                    Log.d("API Error", "Response code: " + response.code());
                    JsonObject errorObject = null;
                    String errorMessage = null;
                    try {
                        String errorBodyString = response.errorBody().string();
                        Log.d("API Error", "Response body: " + errorBodyString);
                        errorObject = new Gson().fromJson(errorBodyString, JsonObject.class);
                        errorMessage = errorObject.get("message").getAsString();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    callback.onConnectionFailure(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    /*
    *
    * User infos request
    *
    * @params int id , string token
    *
    * @return JsonObject
    */
    public void getUserInfos(int id, String token , UserCallback callback) {
        Call<JsonObject> call = userService.getUserInfos(id,token);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                if (jsonObject != null) {
                    Log.d("API Response", "Response code: " + response.code());
                    Log.d("API Response", "Response body: " + jsonObject.toString());
                    callback.onConnectionSuccess(jsonObject);
                } else {
                    Log.d("API Error", "Response code: " + response.code());
                    JsonObject errorObject = null;
                    String errorMessage = null;
                    try {
                        String errorBodyString = response.errorBody().string();
                        Log.d("API Error", "Response body: " + errorBodyString);
                        errorObject = new Gson().fromJson(errorBodyString, JsonObject.class);
                        errorMessage = errorObject.get("message").getAsString();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    callback.onConnectionFailure(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("API Failure", t.getMessage());
                Log.d("API Response Code", String.valueOf(call.request().url()));
                callback.onConnectionFailure(t.getMessage());
            }

        });

    }


    public void getUser(String userInfo) {
        Call<JsonObject> call = userService.getUserInfos(userInfo);
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

    public void searchProducts(String search, ProductsCallback callback) {
        Call<JsonObject> call = productService.searchProducts(search);
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

    public void checkPassword(String token , String pwd, OnValidatePasswordCallback callback) {
        Call<JsonObject> call = userService.checkPassword(token ,new CheckPwd(pwd));

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();

                if (jsonObject != null) {
                    Log.d("1st", "I am here ");
                    Log.d("API Response", "Response code: " + response.code());
                    Log.d("API Response", "Response body: " + jsonObject.toString());
                    if (jsonObject.get("result").getAsString().equals("true")) {
                        Log.d("API Response", "Response body: " + jsonObject.toString());
                        callback.onValidatePasswordSuccess(jsonObject);
                    } else {
                        callback.onValidatePasswordFailure(jsonObject);
                    }
                } else {
                    JsonObject errorObject;
                    String errorMessage;
                    try {

                        String errorBodyString = response.errorBody().string();
                        Log.d("API Error", "Response body: " + errorBodyString);
                        errorObject = new Gson().fromJson(errorBodyString, JsonObject.class);
                        errorMessage = errorObject.get("message").getAsString();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    callback.onValidatePasswordFailure(errorObject);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("API Failure", t.getMessage());
                Log.d("API Response Code", String.valueOf(call.request().url()));
                JsonObject errorObject;

                errorObject = new JsonObject();
                errorObject.addProperty("message", "Server Error");

                callback.onValidatePasswordFailure(errorObject);
            }
        });

    }

    public void updateUser(String token,String changedFirstname, String changedLastname, String changedBday, String changedEmail, UpdateUserCallback updateUserCallback) {
        Call<JsonObject> call = userService.updateUser(token , new UserUpdatedInfos(changedFirstname, changedLastname, changedBday, changedEmail));

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                if (jsonObject != null) {
                    Log.d("API Response", "Response code: " + response.code());
                    Log.d("API Response", "Response body: " + jsonObject.toString());
                    updateUserCallback.onUpdateUserSuccess(jsonObject);
                } else {
                    Log.d("API Response", "Response code: " + response.code());
                    updateUserCallback.onUpdateUserFailure("Response body is null");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("I AM", "HERE");
                updateUserCallback.onUpdateUserFailure(t.getMessage());
            }
        });
    }

    public void updateUserPassword(String token ,String pwd, UpdateUserCallback updateUserCallback) {
        Call<JsonObject> call = userService.updateUserPassword(token,new UpdatePwd(pwd));

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject= response.body();
                if (jsonObject != null) {
                    Log.d("API Response", "Response code: " + response.code());
                    Log.d("API Response", "Response body: " + jsonObject.toString());
                    updateUserCallback.onUpdateUserSuccess(jsonObject);
                } else {
                    updateUserCallback.onUpdateUserFailure("Response body is null");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                updateUserCallback.onUpdateUserFailure(t.getMessage());
            }
        });
    }
}
