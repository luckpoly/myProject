package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by tiashiwang on 2016/4/7.
 */
public class MoneyTableModel {

    /**
     * respCode : 0
     * respMsg : 获取价格配置成功！
     * data : {"id":1,"weight":"4","weightMin":"3","weightSpacing":"1","weightPrice":"1","weightMax":"3","distance":"4","distanceMin":"3","distanceSpacing":"1","distancePrice":"2","distanceMax":"10","addTime":"2016-04-06 14:35:02","updateTime":"2016-04-06 14:35:06"}
     * debugInfo : []
     */

    private int respCode;
    private String respMsg;
    /**
     * id : 1
     * weight : 4
     * weightMin : 3
     * weightSpacing : 1
     * weightPrice : 1
     * weightMax : 3
     * distance : 4
     * distanceMin : 3
     * distanceSpacing : 1
     * distancePrice : 2
     * distanceMax : 10
     * addTime : 2016-04-06 14:35:02
     * updateTime : 2016-04-06 14:35:06
     */

    private DataBean data;
    private List<?> debugInfo;

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
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

    public List<?> getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(List<?> debugInfo) {
        this.debugInfo = debugInfo;
    }

    public static class DataBean {
        private int id;
        private String weight;
        private String weightMin;
        private String weightSpacing;
        private String weightPrice;
        private String weightMax;
        private String distance;
        private String distanceMin;
        private String distanceSpacing;
        private String distancePrice;
        private String distanceMax;
        private String addTime;
        private String updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getWeightMin() {
            return weightMin;
        }

        public void setWeightMin(String weightMin) {
            this.weightMin = weightMin;
        }

        public String getWeightSpacing() {
            return weightSpacing;
        }

        public void setWeightSpacing(String weightSpacing) {
            this.weightSpacing = weightSpacing;
        }

        public String getWeightPrice() {
            return weightPrice;
        }

        public void setWeightPrice(String weightPrice) {
            this.weightPrice = weightPrice;
        }

        public String getWeightMax() {
            return weightMax;
        }

        public void setWeightMax(String weightMax) {
            this.weightMax = weightMax;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getDistanceMin() {
            return distanceMin;
        }

        public void setDistanceMin(String distanceMin) {
            this.distanceMin = distanceMin;
        }

        public String getDistanceSpacing() {
            return distanceSpacing;
        }

        public void setDistanceSpacing(String distanceSpacing) {
            this.distanceSpacing = distanceSpacing;
        }

        public String getDistancePrice() {
            return distancePrice;
        }

        public void setDistancePrice(String distancePrice) {
            this.distancePrice = distancePrice;
        }

        public String getDistanceMax() {
            return distanceMax;
        }

        public void setDistanceMax(String distanceMax) {
            this.distanceMax = distanceMax;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
