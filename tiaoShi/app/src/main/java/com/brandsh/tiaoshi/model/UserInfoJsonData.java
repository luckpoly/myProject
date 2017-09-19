package com.brandsh.tiaoshi.model;

/**
 * Created by apple on 16/2/18.
 */
public class UserInfoJsonData {


    /**
     * resCode : SUCCESS
     * resnMsg : 操作成功
     * data : {"userId":63,"username":"18300709012","icon":"\"\"","sex":"MAN","nickName":"09012","tel":"18300709012","createTime":1463623729,"updateTime":1463627269}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * userId : 63
     * username : 18300709012
     * icon : ""
     * sex : MAN
     * nickName : 09012
     * tel : 18300709012
     * createTime : 1463623729
     * updateTime : 1463627269
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
        private String username;
        private String icon;
        private String sex;
        private String nickName;
        private String tel;
        private String balance;
        private String inviteCode;
        private String healthPartner;
        private String qq;
        private String wx;
        private String pwd;
        private int createTime;
        private int updateTime;

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getWx() {
            return wx;
        }

        public void setWx(String wx) {
            this.wx = wx;
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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public int getCreateTime() {
            return createTime;
        }

        public void setCreateTime(int createTime) {
            this.createTime = createTime;
        }

        public int getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(int updateTime) {
            this.updateTime = updateTime;
        }
    }
}
