package com.brandsh.tiaoshi.model;

import android.graphics.Bitmap;

/**
 * Created by csoul on 15/10/24.
 */
public class ImageModel {

    private String imgKey;
    private Bitmap bitmap;
    private String imgURL;

    public String getImgKey() {
        return imgKey;
    }

    public void setImgKey(String imgKey) {
        this.imgKey = imgKey;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
