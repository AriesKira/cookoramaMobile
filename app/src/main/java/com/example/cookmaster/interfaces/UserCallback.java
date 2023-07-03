package com.example.cookmaster.interfaces;

public interface UserCallback {
    void onConnectionSuccess(int id);
    void onConnectionFailure(String errorMessage);
}
