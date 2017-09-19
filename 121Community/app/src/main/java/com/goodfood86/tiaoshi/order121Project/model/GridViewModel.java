package com.goodfood86.tiaoshi.order121Project.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/12/7.
 */

public class GridViewModel {
    private Drawable drawable;
    private String typeName;
    private int id;
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public GridViewModel() {
    }
    public GridViewModel(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public GridViewModel(String typeName, Drawable drawable) {
        this.typeName = typeName;
        this.drawable = drawable;
    }

    public GridViewModel(Drawable drawable) {
        this.drawable = drawable;
    }

    public GridViewModel(String typeName, int id, Drawable drawable) {
        this.typeName = typeName;
        this.id = id;
        this.drawable = drawable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
