package com.example.cookmaster.classes;

import com.google.gson.annotations.SerializedName;

public class UserUpdatedInfos {
    @SerializedName("firstname")
    private String changedFirstname;
    @SerializedName("lastname")
    private String changedLastname;
    @SerializedName("birthdate")
    private String changedBday;
    @SerializedName("email")
    private String changedEmail;


    public UserUpdatedInfos(String changedFirstname, String changedLastname, String changedBday, String changedEmail) {
        this.changedFirstname = changedFirstname;
        this.changedLastname = changedLastname;
        this.changedBday = changedBday;
        this.changedEmail = changedEmail;
    }
}
