package com.brandsh.tiaoshi.model;

/**
 * Created by Administrator on 2016/10/24.
 */

public class MonthTimeModel {

    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"morningSendTime":"07,08,09","noonSendTime":"11,12,13","nightSendTime":"17,18,19"}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * morningSendTime : 07,08,09
     * noonSendTime : 11,12,13
     * nightSendTime : 17,18,19
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
        private String morningSendTime;
        private String noonSendTime;
        private String nightSendTime;

        public String getMorningSendTime() {
            return morningSendTime;
        }

        public void setMorningSendTime(String morningSendTime) {
            this.morningSendTime = morningSendTime;
        }

        public String getNoonSendTime() {
            return noonSendTime;
        }

        public void setNoonSendTime(String noonSendTime) {
            this.noonSendTime = noonSendTime;
        }

        public String getNightSendTime() {
            return nightSendTime;
        }

        public void setNightSendTime(String nightSendTime) {
            this.nightSendTime = nightSendTime;
        }
    }
}
