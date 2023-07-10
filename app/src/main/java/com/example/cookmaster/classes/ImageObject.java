package com.example.cookmaster.classes;

public class ImageObject {
    private String id;
    private int imagePath;

    public ImageObject(String id, int imagePath) {
        this.id = id;
        this.imagePath = imagePath;
    }

    public int getImagePath() {
        return this.imagePath;
    }

    // Ajoutez des getters et des setters si nécessaire
}
