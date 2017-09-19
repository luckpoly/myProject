package com.brandsh.tiaoshi.model;

/**
 * Created by apple on 16/2/17.
 */
public class LoginJsonData {
    /**
     * resCode : SUCCESS
     * resnMsg : 操作成功
     * data : {"userId":63,"userName":"18300709012","nickName":"09012","tel":"18300709012","sex":"MAN","icon":"\"\"","token":"b3f8c996303d932673ccccf3acb4502d","tokenExpires":7776000}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * userId : 63
     * userName : 18300709012
     * nickName : 09012
     * tel : 18300709012
     * sex : MAN
     * icon : ""
     * token : b3f8c996303d932673ccccf3acb4502d
     * tokenExpires : 7776000
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
        private int userId;
        private String userName;
        private String nickName;
        private String sex;
        private String icon;
        private String balance;
        private String inviteCode;
        private String healthPartner;
        private String accessToken;
        private String accessOverTime;
        private String refreshToken;
        private String refreshOverTime;
        private String intro;
        private String name;
        private String phone;
        private int tokenExpires;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getAccessOverTime() {
            return accessOverTime;
        }

        public void setAccessOverTime(String accessOverTime) {
            this.accessOverTime = accessOverTime;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public String getRefreshOverTime() {
            return refreshOverTime;
        }

        public void setRefreshOverTime(String refreshOverTime) {
            this.refreshOverTime = refreshOverTime;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getHealthPartner() {
            return healthPartner;
        }

        public void setHealthPartner(String healthPartner) {
            this.healthPartner = healthPartner;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }


        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }


        public int getTokenExpires() {
            return tokenExpires;
        }

        public void setTokenExpires(int tokenExpires) {
            this.tokenExpires = tokenExpires;
        }
    }
}
