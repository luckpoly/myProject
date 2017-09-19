package com.brandsh.tiaoshi.model;

/**
 * Created by apple on 16/2/22.
 */
public class GuanZhuStatusJsonData {

    /**
     * respCode : 0
     * respMsg : ok
     * data : {"status":1}
     * debugInfo :
     */

    private int respCode;
    private String respMsg;
    /**
     * status : 1
     */

    private DataEntity data;
    private String debugInfo;

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setDebugInfo(String debugInfo) {
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

    public String getDebugInfo() {
        return debugInfo;
    }

    public static class DataEntity {
        private int status;

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }
    }
}
