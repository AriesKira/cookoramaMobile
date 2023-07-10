package com.example.cookmaster.classes;

import com.google.gson.annotations.SerializedName;

public class CheckPwd {
    @SerializedName("password")
    private String password;

    public CheckPwd(String password) {
        this.password = password;
    }
}
