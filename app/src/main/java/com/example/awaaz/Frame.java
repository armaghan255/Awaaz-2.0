package com.example.awaaz;

import android.graphics.Bitmap;

public class Frame {
    int id;
    Bitmap image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Frame(int id, Bitmap image) {
        this.id = id;
        this.image = image;
    }
}
