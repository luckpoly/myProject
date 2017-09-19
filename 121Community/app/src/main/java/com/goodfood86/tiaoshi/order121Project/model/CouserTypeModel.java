package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by Administrator on 2016/12/16.
 */

public class CouserTypeModel {

    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : [{"id":"4","name":"作文"},{"id":"3","name":"英语"},{"id":"2","name":"语文"},{"id":"1","name":"数学"}]
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    private String debugInfo;
    /**
     * id : 4
     * name : 作文
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
    }
}
