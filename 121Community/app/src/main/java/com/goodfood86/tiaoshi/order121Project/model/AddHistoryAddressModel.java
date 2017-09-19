package com.goodfood86.tiaoshi.order121Project.model;

/**
 * Created by Administrator on 2016/4/6.
 */
public class AddHistoryAddressModel {

    /**
     * respCode : 0
     * respMsg : 修改成功
     * data : {"id":"12","city":"sdsd","area":"sdsd","address":"sdsd","addressDetail":"sdsd","isDefault":1,"lng":"sdsd","lat":"sdsd","addTime":"2016-01-07 18:50:05","updateTime":"2016-01-07 18:50:05"}
     */

    private int respCode;
    private String respMsg;
    /**
     * id : 12
     * city : sdsd
     * area : sdsd
     * address : sdsd
     * addressDetail : sdsd
     * isDefault : 1
     * lng : sdsd
     * lat : sdsd
     * addTime : 2016-01-07 18:50:05
     * updateTime : 2016-01-07 18:50:05
     */

    private DataEntity data;

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public void setData(DataEntity data) {
        this.data = data;
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

    public static class DataEntity {
        private String id;
        private String city;
        private String area;
        private String address;
        private String addressDetail;
        private int isDefault;
        private String lng;
        private String lat;
        private String addTime;
        private String updateTime;

        public void setId(String id) {
            this.id = id;
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

        public void setIsDefault(int isDefault) {
            this.isDefault = isDefault;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getId() {
            return id;
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

        public int getIsDefault() {
            return isDefault;
        }

        public String getLng() {
            return lng;
        }

        public String getLat() {
            return lat;
        }

        public String getAddTime() {
            return addTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }
    }
}
