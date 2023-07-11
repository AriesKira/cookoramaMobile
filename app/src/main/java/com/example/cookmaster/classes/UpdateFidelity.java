package com.example.cookmaster.classes;

import com.google.gson.annotations.SerializedName;

public class UpdateFidelity {
    @SerializedName("fidelity")
    private int fidelity;

    public UpdateFidelity(int fidelity) {
        this.fidelity = fidelity;
    }

}
