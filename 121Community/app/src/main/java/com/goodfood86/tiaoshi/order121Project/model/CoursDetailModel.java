package com.goodfood86.tiaoshi.order121Project.model;

/**
 * Created by Administrator on 2016/12/16.
 */

public class CoursDetailModel {

    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"id":"13","name":"满分作文","courseTypeName":"作文","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1481610389640916.jpg","intro":"","link":"","score":"5","school":"至诚学校"}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * id : 13
     * name : 满分作文
     * courseTypeName : 作文
     * icon : http://7xn4ae.com1.z0.glb.clouddn.com/1481610389640916.jpg
     * intro :
     * link :
     * score : 5
     * school : 至诚学校
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
        private String id;
        private String name;
        private String courseTypeName;
        private String icon;
        private String intro;
        private String link;
        private String score;
        private String school;
        /**
         * title : 宝乐迪量版式KTV(安波路)
         * distance :
         * content : EEE
         * address : 上海市杨浦区安波路533号
         * addressDetail : 安波路533弄6
         * tel : 021-65506777
         * perCost : 68
         */

        private String title;
        private String distance;
        private String content;
        private String address;
        private String addressDetail;
        private String tel;
        private String perCost;

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

        public String getCourseTypeName() {
            return courseTypeName;
        }

        public void setCourseTypeName(String courseTypeName) {
            this.courseTypeName = courseTypeName;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getPerCost() {
            return perCost;
        }

        public void setPerCost(String perCost) {
            this.perCost = perCost;
        }
    }
}
