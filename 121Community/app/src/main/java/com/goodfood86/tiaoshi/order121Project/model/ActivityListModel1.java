package com.goodfood86.tiaoshi.order121Project.model;

import android.graphics.drawable.Drawable;

import java.util.List;

/**
 * Created by Administrator on 2016/8/5.
 */
public class ActivityListModel1 {

    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : [{"id":"1","name":"苹果","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1470380593930.jpg","startTime":"1470614880","omName":"习惯","applyCount":"0","isApplay":"OFF"},{"id":"2","name":"苹果","img":"","startTime":"1470614880","omName":"习惯","applyCount":"0","isApplay":"OFF"},{"id":"3","name":"打卡佛书","img":"","startTime":"1470787680","omName":"习惯","applyCount":"0","isApplay":"OFF"},{"id":"4","name":"哈哈哈","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1470386579187.jpg","startTime":"1470960480","omName":"习惯","applyCount":"0","isApplay":"OFF"},{"id":"5","name":"旅游","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1470622726618.jpg","startTime":"1473293280","omName":"习惯","applyCount":"0","isApplay":"ON"},{"id":"6","name":"游玩。。。","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1470628903879.jpg","startTime":"1471738080","omName":"习惯","applyCount":"0","isApplay":"ON"},{"id":"7","name":"默默的","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1470631028993.jpg","startTime":"1478995680","omName":"习惯","applyCount":"0","isApplay":"ON"},{"id":"8","name":"茉莉花茶叶蛋","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1470644848213.jpg","startTime":"1473293280","omName":"习惯","applyCount":"1","isApplay":"ON"},{"id":"9","name":"青岛旅游","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1470645176489.jpg","startTime":"1473293280","omName":"习惯","applyCount":"10","isApplay":"OFF"},{"id":"10","name":"吃饭","img":"","startTime":"1470835500","omName":"","applyCount":"8","isApplay":"OFF"},{"id":"11","name":"一起吃饭","img":"http://7xn4ae.com1.z0.glb.clouddn.com//FklEIbXax5iFVZyzwBJtDrj6Nbek","startTime":"1470796497","omName":"","applyCount":"4","isApplay":"OFF"}]
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    private String debugInfo;
    /**
     * id : 1
     * name : 苹果
     * img : http://7xn4ae.com1.z0.glb.clouddn.com/1470380593930.jpg
     * startTime : 1470614880
     * omName : 习惯
     * applyCount : 0
     * isApplay : OFF
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
        private String name;
        private String img;
        private String startTime;
        private String endTime;
        private Drawable drawable;

        public Drawable getDrawable() {
            return drawable;
        }

        public void setDrawable(Drawable drawable) {
            this.drawable = drawable;
        }

        private String omName;
        private String applyCount;
        private String isApply;
        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getOmName() {
            return omName;
        }

        public void setOmName(String omName) {
            this.omName = omName;
        }

        public String getApplyCount() {
            return applyCount;
        }

        public void setApplyCount(String applyCount) {
            this.applyCount = applyCount;
        }

        public String getIsApply() {
            return isApply;
        }

        public void setIsApply(String isApplay) {
            this.isApply = isApplay;
        }
    }
}
