package com.example.cookmaster.classes;

import android.widget.ImageButton;

public class ButtonObject {
    private ImageButton imageButton;
    private int imagePath;

    public ButtonObject(ImageButton imageButton, int imagePath) {
        this.imageButton= imageButton;
        this.imagePath = imagePath;
    }

    public int getImagePath() {
        return this.imagePath;
    }

    public ImageButton getImageButton() {
        return this.imageButton;
    }


}
