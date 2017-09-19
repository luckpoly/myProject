package com.brandsh.tiaoshi.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sisi on 16/3/1.
 */
public class DiyShoppingCartJsonData implements Serializable{
    private String shop_id;
    private int goods_total_count;
    private double goods_total_cash;

    public int getGoods_total_count() {
        return goods_total_count;
    }

    public void setGoods_total_count(int goods_total_count) {
        this.goods_total_count = goods_total_count;
    }

    public double getGoods_total_cash() {
        return goods_total_cash;
    }

    public void setGoods_total_cash(double goods_total_cash) {
        this.goods_total_cash = goods_total_cash;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    private List<GoodsListEntity> goodsList;


    public List<GoodsListEntity> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsListEntity> goodsList) {
        this.goodsList = goodsList;
    }

    public static class GoodsListEntity implements Serializable{
        private String goods_id;
        private String goods_name;
        private String goods_price;
        private String unit;
        private String price;
        private int goods_count;
        private int  type=0;
        private String typename;
        private String sales_unit;

        public String getTypename() {
            return typename;
        }

        public void setTypename(String typename) {
            this.typename = typename;
        }

        public String getSales_unit() {
            return sales_unit;
        }

        public void setSales_unit(String sales_unit) {
            this.sales_unit = sales_unit;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getGoods_count() {
            return goods_count;
        }

        public void setGoods_count(int goods_count) {
            this.goods_count = goods_count;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
