package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by Administrator on 2016/8/11.
 */
public class YuyueListModel {

    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : [{"moduleCategoryMakeId":"3","moduleCategoryId":"15","moduleCategoryName":"微整理","moduleCategoryCode":"PurifierGlass","time":"1470614880","name":"杜勇","phone":"18300709012","address":"三门路桥","addressDetail":"1101","remark":"哦哦哦","createTime":"1470831097","status":"MAKE","lng":"121.48794","lat":"31.311541"},{"moduleCategoryMakeId":"4","moduleCategoryId":"15","moduleCategoryName":"微整理","moduleCategoryCode":"PurifierGlass","time":"1491955680","name":"杜勇","phone":"18300709012","address":"上海市宝山区三门路靠近复旦软件园(高境)","addressDetail":"1102","remark":"快回去","createTime":"1470883709","status":"MAKE","lng":"121.48754882812","lat":"31.312595367432"}]
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    private String debugInfo;
    /**
     * moduleCategoryMakeId : 3
     * moduleCategoryId : 15
     * moduleCategoryName : 微整理
     * moduleCategoryCode : PurifierGlass
     * time : 1470614880
     * name : 杜勇
     * phone : 18300709012
     * address : 三门路桥
     * addressDetail : 1101
     * remark : 哦哦哦
     * createTime : 1470831097
     * status : MAKE
     * lng : 121.48794
     * lat : 31.311541
     */

    private List<DataBean> data;

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

    public String getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String moduleCategoryMakeId;
        private String moduleCategoryId;
        private String moduleCategoryName;
        private String moduleCategoryCode;
        private String time;
        private String name;
        private String phone;
        private String address;
        private String addressDetail;
        private String remark;
        private String createTime;
        private String status;
        private String lng;
        private String lat;

        public String getModuleCategoryMakeId() {
            return moduleCategoryMakeId;
        }

        public void setModuleCategoryMakeId(String moduleCategoryMakeId) {
            this.moduleCategoryMakeId = moduleCategoryMakeId;
        }

        public String getModuleCategoryId() {
            return moduleCategoryId;
        }

        public void setModuleCategoryId(String moduleCategoryId) {
            this.moduleCategoryId = moduleCategoryId;
        }

        public String getModuleCategoryName() {
            return moduleCategoryName;
        }

        public void setModuleCategoryName(String moduleCategoryName) {
            this.moduleCategoryName = moduleCategoryName;
        }

        public String getModuleCategoryCode() {
            return moduleCategoryCode;
        }

        public void setModuleCategoryCode(String moduleCategoryCode) {
            this.moduleCategoryCode = moduleCategoryCode;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddressDetail() {
            return addressDetail;
        }

        public void setAddressDetail(String addressDetail) {
            this.addressDetail = addressDetail;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }
}
