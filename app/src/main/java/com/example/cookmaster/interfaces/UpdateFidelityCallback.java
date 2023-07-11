package com.example.cookmaster.interfaces;

import com.google.gson.JsonObject;

public interface UpdateFidelityCallback {
    void onUpdateFidelitySuccess(JsonObject userInfos);
    void onUpdateFidelityFailure(JsonObject errorMessage);
}
