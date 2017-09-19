package com.brandsh.tiaoshi.model;

/**
 * Created by apple on 16/2/17.
 */
public class SMSCodejsonData {


    /**
     * resCode : SUCCESS
     * resnMsg : 操作成功
     * data : {"uuid":"573c3dac9769f","expires":300}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * uuid : 573c3dac9769f
     * expires : 300
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
        private String uuid;
        private int expires;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public int getExpires() {
            return expires;
        }

        public void setExpires(int expires) {
            this.expires = expires;
        }
    }
}
