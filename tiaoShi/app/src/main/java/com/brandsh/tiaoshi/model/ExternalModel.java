package com.brandsh.tiaoshi.model;

/**
 * Created by Administrator on 2017/1/13.
 */

public class ExternalModel {

    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"only":"NO"}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * only : NO
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
        private String only;

        public String getOnly() {
            return only;
        }

        public void setOnly(String only) {
            this.only = only;
        }
    }
}
