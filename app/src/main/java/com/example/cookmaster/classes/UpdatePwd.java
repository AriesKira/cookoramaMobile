package com.example.cookmaster.classes;

import com.google.gson.annotations.SerializedName;

public class UpdatePwd {
    @SerializedName("password")
    private String password;

    public UpdatePwd(String password) {
        this.password = password;
    }
}
