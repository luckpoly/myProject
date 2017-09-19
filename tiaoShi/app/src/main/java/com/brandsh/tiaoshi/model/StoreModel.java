package com.brandsh.tiaoshi.model;

import java.util.List;

/**
 * Created by libokang on 15/10/26.
 */
public class StoreModel {

    //真实数据
    private String storeId;
    private String storeImgURL;
    private String storeName;
    private String storeAddress;
    private String distance;
    private String categoryCount;
    private String starCount;

    private String shopName;
    private String imgKey;
    private String cityName;

    private String tel;
    private String marketCount;
    private String lat;
    private String lng;


    //模拟数据
    private String id;
    private String imgURL;
    private String address;
    private String storeDesc;


    public String getStarCount() {
        return starCount;
    }

    public void setStarCount(String starCount) {
        this.starCount = starCount;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getMarketCount() {
        return marketCount;
    }

    public void setMarketCount(String marketCount) {
        this.marketCount = marketCount;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getImgKey() {
        return imgKey;
    }

    public void setImgKey(String imgKey) {
        this.imgKey = imgKey;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreImgURL() {
        return storeImgURL;
    }

    public void setStoreImgURL(String storeImgURL) {
        this.storeImgURL = storeImgURL;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCategoryCount() {
        return categoryCount;
    }

    public void setCategoryCount(String categoryCount) {
        this.categoryCount = categoryCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStoreDesc() {
        return storeDesc;
    }

    public void setStoreDesc(String storeDesc) {
        this.storeDesc = storeDesc;
    }
}
