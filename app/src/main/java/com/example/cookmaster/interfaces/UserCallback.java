package com.example.cookmaster.interfaces;

import com.google.gson.JsonObject;

public interface UserCallback {
    void onConnectionSuccess(JsonObject userInfos);
    void onConnectionFailure(String errorMessage);
}
