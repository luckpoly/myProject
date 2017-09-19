package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by tiashiwang on 2016/4/1.
 */
public class VerifyModel {


    /**
     * respCode : 0
     * respMsg : 短信验证码发送成功
     * data : {"type":1,"code":"835690","phone":"17702182420","resId":"20160401171358","resCode":"0","content":"[121]您的短信验证码是：835690.诚挚欢迎您加入国内配送速度最快最快的配送团队!","sendTime":"2016-04-01 17:13:54","expireTime":1800,"id":"345"}
     * debugInfo : []
     */

    private int respCode;
    private String respMsg;
    /**
     * type : 1
     * code : 835690
     * phone : 17702182420
     * resId : 20160401171358
     * resCode : 0
     * content : [121]您的短信验证码是：835690.诚挚欢迎您加入国内配送速度最快最快的配送团队!
     * sendTime : 2016-04-01 17:13:54
     * expireTime : 1800
     * id : 345
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
        private int type;
        private String code;
        private String phone;
        private String resId;
        private String resCode;
        private String content;
        private String sendTime;
        private int expireTime;
        private String id;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getResId() {
            return resId;
        }

        public void setResId(String resId) {
            this.resId = resId;
        }

        public String getResCode() {
            return resCode;
        }

        public void setResCode(String resCode) {
            this.resCode = resCode;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public int getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(int expireTime) {
            this.expireTime = expireTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
