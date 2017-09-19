package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by Administrator on 2016/4/14.
 */
public class EvaluateModel {

    /**
     * respCode : 0
     * respMsg : OK
     * data : {"id":8,"id_order":265,"id_user":131,"content":"测试","time_create":1460596290,"time_update":1460596290,"status_display":1}
     * debugInfo : []
     */

    private int respCode;
    private String respMsg;
    /**
     * id : 8
     * id_order : 265
     * id_user : 131
     * content : 测试
     * time_create : 1460596290
     * time_update : 1460596290
     * status_display : 1
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
        private int id_order;
        private int id_user;
        private String content;
        private int time_create;
        private int time_update;
        private int status_display;

        public void setId(int id) {
            this.id = id;
        }

        public void setId_order(int id_order) {
            this.id_order = id_order;
        }

        public void setId_user(int id_user) {
            this.id_user = id_user;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setTime_create(int time_create) {
            this.time_create = time_create;
        }

        public void setTime_update(int time_update) {
            this.time_update = time_update;
        }

        public void setStatus_display(int status_display) {
            this.status_display = status_display;
        }

        public int getId() {
            return id;
        }

        public int getId_order() {
            return id_order;
        }

        public int getId_user() {
            return id_user;
        }

        public String getContent() {
            return content;
        }

        public int getTime_create() {
            return time_create;
        }

        public int getTime_update() {
            return time_update;
        }

        public int getStatus_display() {
            return status_display;
        }
    }
}
