package com.adara.yashsd.kadmus;

import android.graphics.Bitmap;

public class ImageItem {
    private Bitmap imageResource;
    private String id;

    public ImageItem(Bitmap imageResource, String id){
        this.imageResource = imageResource;
        this.id = id;
    }

    public Bitmap getImageResource(){
        return imageResource;
    }

    public String getId(){
        return id;
    }

}