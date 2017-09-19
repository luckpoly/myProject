package com.brandsh.tiaoshi.model;

import java.io.Serializable;

/**
 * Created by libokang on 15/11/5.
 */
public class ConsigneeModel implements Serializable {

    private String id;
    private String address;
    private String name;
    private String tel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
