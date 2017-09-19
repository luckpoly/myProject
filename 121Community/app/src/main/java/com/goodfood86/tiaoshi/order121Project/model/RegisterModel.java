package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by tiashiwang on 2016/4/1.
 */
public class RegisterModel {


    /**
     * respCode : 0
     * respMsg : 注册成功
     * data : {"id":131,"account":"20160401309807","amount":0,"freeze":0,"userType":1,"username":"","nickname":"","phone":"18300709012","email":"","sex":1,"birthday":"0000-00-00","imgKey":"","regTime":"2016-04-01 18:00:40","inviteId":0,"inviteCode":"HhVQ3vpl","identifyCode":"","identifyPic1":"","identifyPic2":"","lastLoginTime":"2016-04-01 18:00:40","isRest":0,"restTime":"0000-00-00 00:00:00","lastUpdateTime":"2016-04-01 18:00:40","lng":0,"lat":0,"geo_hash":"","cityId":0,"areaId":0,"status":0,"token":"80ca0f76b6534d30f07066644e79a2de"}
     * debugInfo : []
     */

    private int respCode;
    private String respMsg;
    /**
     * id : 131
     * account : 20160401309807
     * amount : 0
     * freeze : 0
     * userType : 1
     * username :
     * nickname :
     * phone : 18300709012
     * email :
     * sex : 1
     * birthday : 0000-00-00
     * imgKey :
     * regTime : 2016-04-01 18:00:40
     * inviteId : 0
     * inviteCode : HhVQ3vpl
     * identifyCode :
     * identifyPic1 :
     * identifyPic2 :
     * lastLoginTime : 2016-04-01 18:00:40
     * isRest : 0
     * restTime : 0000-00-00 00:00:00
     * lastUpdateTime : 2016-04-01 18:00:40
     * lng : 0
     * lat : 0
     * geo_hash :
     * cityId : 0
     * areaId : 0
     * status : 0
     * token : 80ca0f76b6534d30f07066644e79a2de
     */

    private DataBean data;
    private List<?> debugInfo;

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
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

    public List<?> getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(List<?> debugInfo) {
        this.debugInfo = debugInfo;
    }

    public static class DataBean {
        private int id;
        private String account;
        private int amount;
        private int freeze;
        private int userType;
        private String username;
        private String nickname;
        private String phone;
        private String email;
        private int sex;
        private String birthday;
        private String imgKey;
        private String regTime;
        private int inviteId;
        private String inviteCode;
        private String identifyCode;
        private String identifyPic1;
        private String identifyPic2;
        private String lastLoginTime;
        private int isRest;
        private String restTime;
        private String lastUpdateTime;
        private int lng;
        private int lat;
        private String geo_hash;
        private int cityId;
        private int areaId;
        private int status;
        private String token;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getFreeze() {
            return freeze;
        }

        public void setFreeze(int freeze) {
            this.freeze = freeze;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getImgKey() {
            return imgKey;
        }

        public void setImgKey(String imgKey) {
            this.imgKey = imgKey;
        }

        public String getRegTime() {
            return regTime;
        }

        public void setRegTime(String regTime) {
            this.regTime = regTime;
        }

        public int getInviteId() {
            return inviteId;
        }

        public void setInviteId(int inviteId) {
            this.inviteId = inviteId;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public String getIdentifyCode() {
            return identifyCode;
        }

        public void setIdentifyCode(String identifyCode) {
            this.identifyCode = identifyCode;
        }

        public String getIdentifyPic1() {
            return identifyPic1;
        }

        public void setIdentifyPic1(String identifyPic1) {
            this.identifyPic1 = identifyPic1;
        }

        public String getIdentifyPic2() {
            return identifyPic2;
        }

        public void setIdentifyPic2(String identifyPic2) {
            this.identifyPic2 = identifyPic2;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public int getIsRest() {
            return isRest;
        }

        public void setIsRest(int isRest) {
            this.isRest = isRest;
        }

        public String getRestTime() {
            return restTime;
        }

        public void setRestTime(String restTime) {
            this.restTime = restTime;
        }

        public String getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(String lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        public int getLng() {
            return lng;
        }

        public void setLng(int lng) {
            this.lng = lng;
        }

        public int getLat() {
            return lat;
        }

        public void setLat(int lat) {
            this.lat = lat;
        }

        public String getGeo_hash() {
            return geo_hash;
        }

        public void setGeo_hash(String geo_hash) {
            this.geo_hash = geo_hash;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
