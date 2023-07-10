package com.example.cookmaster.interfaces;

import com.google.gson.JsonObject;

public interface UpdateUserCallback {
    void onUpdateUserSuccess(JsonObject userInfos);
    void onUpdateUserFailure(String errorMessage);
}
