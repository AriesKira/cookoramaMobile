package com.example.cookmaster.interfaces;

public interface LoginUserCallback {
    void onConnectionSuccess(String token,int id);
    void onConnectionFailure(String errorMessage);

}
