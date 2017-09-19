package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by Administrator on 2016/12/11.
 */

public class CustomActivityModel {

    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : [{"id":"10","img":"","name":"","startTime":"1469980800","endTime":"1469980800","omName":"","isApplay":"ON|OFF","applyCount":"0"}]
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    private String debugInfo;
    /**
     * id : 10
     * img :
     * name :
     * startTime : 1469980800
     * endTime : 1469980800
     * omName :
     * isApplay : ON|OFF
     * applyCount : 0
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
        private String id;
        private String img;
        private String name;
        private String startTime;
        private String endTime;
        private String omName;
        private String isApplay;
        private String applyCount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getOmName() {
            return omName;
        }

        public void setOmName(String omName) {
            this.omName = omName;
        }

        public String getIsApplay() {
            return isApplay;
        }

        public void setIsApplay(String isApplay) {
            this.isApplay = isApplay;
        }

        public String getApplyCount() {
            return applyCount;
        }

        public void setApplyCount(String applyCount) {
            this.applyCount = applyCount;
        }
    }
}
