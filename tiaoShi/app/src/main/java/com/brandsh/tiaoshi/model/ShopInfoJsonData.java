package com.brandsh.tiaoshi.model;

import java.io.Serializable;

/**
 * Created by sisi on 16/3/21.
 */
public class ShopInfoJsonData implements Serializable {

    /**
     * respCode : 0
     * data : {"status":"2","shop_name":"甜甜水果","min_cost":"1","shop_id":"46"}
     * debugInfo :
     * respMsg : ok
     */

    private int respCode;
    /**
     * status : 2
     * shop_name : 甜甜水果
     * min_cost : 1
     * shop_id : 46
     */

    private DataEntity data;
    private String debugInfo;
    private String respMsg;

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public int getRespCode() {
        return respCode;
    }

    public DataEntity getData() {
        return data;
    }

    public String getDebugInfo() {
        return debugInfo;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public static class DataEntity implements Serializable{
        private String status;
        private String shop_name;
        private String min_cost;
        private String shop_id;

        public void setStatus(String status) {
            this.status = status;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public void setMin_cost(String min_cost) {
            this.min_cost = min_cost;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getStatus() {
            return status;
        }

        public String getShop_name() {
            return shop_name;
        }

        public String getMin_cost() {
            return min_cost;
        }

        public String getShop_id() {
            return shop_id;
        }
    }
}
