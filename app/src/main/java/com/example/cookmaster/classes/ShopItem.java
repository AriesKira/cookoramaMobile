package com.example.cookmaster.classes;

import android.util.Log;

import java.io.Serializable;

public class ShopItem implements Serializable {
    private String name;
    private String description;
    private String price;

    private String quantity;

    private int id;
    private String image = "https://cookorama.fr/ressources/images/shopImage/";

    public ShopItem(String name, String description, String price, String quantity, int id, String image) {
        this.name = name;
        this.description = description;
        this.price = price + "€";
        this.quantity = quantity;
        this.id = id;
        this.image = this.image + image;
        Log.d("ApiImage", "ImgUrl: " + this.image);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "ShopItem{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", quantity='" + quantity + '\'' +
                ", id=" + id +
                ", image='" + image + '\'' +
                '}';
    }
}
