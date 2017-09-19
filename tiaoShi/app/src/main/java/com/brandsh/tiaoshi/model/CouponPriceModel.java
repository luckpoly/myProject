package com.brandsh.tiaoshi.model;

/**
 * Created by Administrator on 2016/11/15.
 */

public class CouponPriceModel {


    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"total":44.7,"couponPrice":6.71,"shopCouponPrice":0,"sendFree":3}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * total : 44.7
     * couponPrice : 6.71
     * shopCouponPrice : 0
     * sendFree : 3
     */

    private DataBean data;
    private String debugInfo;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }

    public static class DataBean {
        private String total;
        private String couponPrice;
        private String shopCouponPrice;
        private String sendFree;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getCouponPrice() {
            return couponPrice;
        }

        public void setCouponPrice(String couponPrice) {
            this.couponPrice = couponPrice;
        }

        public String getShopCouponPrice() {
            return shopCouponPrice;
        }

        public void setShopCouponPrice(String shopCouponPrice) {
            this.shopCouponPrice = shopCouponPrice;
        }

        public String getSendFree() {
            return sendFree;
        }

        public void setSendFree(String sendFree) {
            this.sendFree = sendFree;
        }
    }
}
