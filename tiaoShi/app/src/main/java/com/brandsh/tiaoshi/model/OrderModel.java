package com.brandsh.tiaoshi.model;

import java.io.Serializable;

/**
 * Created by libokang on 15/11/11.
 */
public class OrderModel implements Serializable {

    public static final String ORDER_NEED_PAY = "0";
    public static final String ORDER_PAYED = "1";
    public static final String ORDER_SENDING = "2";
    public static final String ORDER_WAIT_EVALUATE = "3";
    public static final String ORDER_FINISHED = "5";

    private String orderId;//订单id
    private String orderCode;// 订单编号
    private String productId;// 商品id
    private String imgURL; //图片
    private String productName;// 商品名字
    private String price;//商品价格
    private String count;// 计量
    private String unit;// 计量单位
    private String buyCount;// 购买数量
    private String total;//订单总价
    private String orderState;//0：未付款 1：已付款 2：已发货 3：已签收 4：待评价 5：交易完成 6：订单取消（退货之类的，再议）


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(String buyCount) {
        this.buyCount = buyCount;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }
}
