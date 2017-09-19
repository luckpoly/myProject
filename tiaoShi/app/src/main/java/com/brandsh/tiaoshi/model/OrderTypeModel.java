package com.brandsh.tiaoshi.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2017/1/5.
 */

public class OrderTypeModel {
    Drawable d;
    String name;
    String type;

    public OrderTypeModel() {
    }

    public OrderTypeModel(Drawable d, String name, String type) {
        this.d = d;
        this.name = name;
        this.type = type;
    }

    public Drawable getD() {
        return d;
    }

    public void setD(Drawable d) {
        this.d = d;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
