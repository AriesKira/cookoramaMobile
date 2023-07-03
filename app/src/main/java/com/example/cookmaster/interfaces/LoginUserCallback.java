package com.example.cookmaster.interfaces;

public interface LoginUserCallback {
    void onConnectionSuccess(String token);
    void onConnectionFailure(String errorMessage);

}
