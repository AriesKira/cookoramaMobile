package com.example.cookmaster.interfaces;

import com.google.gson.JsonObject;

public interface OnValidatePasswordCallback {
    void onValidatePasswordSuccess(JsonObject token);
    void onValidatePasswordFailure(JsonObject errorObject);
}
