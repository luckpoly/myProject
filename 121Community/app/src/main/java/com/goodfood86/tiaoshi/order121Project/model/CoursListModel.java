package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by Administrator on 2016/12/16.
 */

public class CoursListModel {

    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : [{"id":"10","name":"","courseTypeName":"","icon":"","link":"","score":"","school":""}]
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    private String debugInfo;
    /**
     * id : 10
     * name :
     * courseTypeName :
     * icon :
     * link :
     * score :
     * school :
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
        private String courseTypeName;
        private String icon;
        private String link;
        private String score;
        private String school;

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
    }
}
