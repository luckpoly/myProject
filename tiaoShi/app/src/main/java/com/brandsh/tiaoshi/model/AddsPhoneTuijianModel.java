package com.brandsh.tiaoshi.model;

/**
 * Created by Administrator on 2016/12/30.
 */

public class AddsPhoneTuijianModel {

    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"phones":"13111111111,13211111111"}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * phones : 13111111111,13211111111
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
        private String phones;

        public String getPhones() {
            return phones;
        }

        public void setPhones(String phones) {
            this.phones = phones;
        }
    }
}
