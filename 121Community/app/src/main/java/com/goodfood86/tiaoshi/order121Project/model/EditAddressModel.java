package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by Administrator on 2016/4/6.
 */
public class EditAddressModel {

    /**
     * respCode : 0
     * respMsg : 添加地址成功！
     * data : {"id":8,"uid":132,"city":"上海市","area":"中山东一路","address":"外滩","addressDetail":"11","lng":121.49060058594,"lat":31.237766265869,"isDefault":0,"addTime":"2016-04-06 19:40:18","updateTime":"2016-04-06 19:40:18"}
     * debugInfo : []
     */

    private int respCode;
    private String respMsg;
    /**
     * id : 8
     * uid : 132
     * city : 上海市
     * area : 中山东一路
     * address : 外滩
     * addressDetail : 11
     * lng : 121.49060058594
     * lat : 31.237766265869
     * isDefault : 0
     * addTime : 2016-04-06 19:40:18
     * updateTime : 2016-04-06 19:40:18
     */

    private DataEntity data;
    private List<?> debugInfo;

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setDebugInfo(List<?> debugInfo) {
        this.debugInfo = debugInfo;
    }

    public int getRespCode() {
        return respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public DataEntity getData() {
        return data;
    }

    public List<?> getDebugInfo() {
        return debugInfo;
    }

    public static class DataEntity {
        private int id;
        private int uid;
        private String city;
        private String area;
        private String address;
        private String addressDetail;
        private double lng;
        private double lat;
        private int isDefault;
        private String addTime;
        private String updateTime;

        public void setId(int id) {
            this.id = id;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setAddressDetail(String addressDetail) {
            this.addressDetail = addressDetail;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public void setIsDefault(int isDefault) {
            this.isDefault = isDefault;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getId() {
            return id;
        }

        public int getUid() {
            return uid;
        }

        public String getCity() {
            return city;
        }

        public String getArea() {
            return area;
        }

        public String getAddress() {
            return address;
        }

        public String getAddressDetail() {
            return addressDetail;
        }

        public double getLng() {
            return lng;
        }

        public double getLat() {
            return lat;
        }

        public int getIsDefault() {
            return isDefault;
        }

        public String getAddTime() {
            return addTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }
    }
}
