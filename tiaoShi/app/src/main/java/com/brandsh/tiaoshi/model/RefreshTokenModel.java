package com.brandsh.tiaoshi.model;

/**
 * Created by Administrator on 2017/1/6.
 */

public class RefreshTokenModel {

    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"refreshToken":"xxxx","refreshTokenOverTime":"12121","accessToken":"xxxxx","accessTokenOverTime":"12121","isTemp":"YES|NO"}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * refreshToken : xxxx
     * refreshTokenOverTime : 12121
     * accessToken : xxxxx
     * accessTokenOverTime : 12121
     * isTemp : YES|NO
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
        private String refreshToken;
        private String refreshTokenOverTime;
        private String accessToken;
        private String accessTokenOverTime;
        private String isTemp;

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public String getRefreshTokenOverTime() {
            return refreshTokenOverTime;
        }

        public void setRefreshTokenOverTime(String refreshTokenOverTime) {
            this.refreshTokenOverTime = refreshTokenOverTime;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getAccessTokenOverTime() {
            return accessTokenOverTime;
        }

        public void setAccessTokenOverTime(String accessTokenOverTime) {
            this.accessTokenOverTime = accessTokenOverTime;
        }

        public String getIsTemp() {
            return isTemp;
        }

        public void setIsTemp(String isTemp) {
            this.isTemp = isTemp;
        }
    }
}
