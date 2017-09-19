package com.goodfood86.tiaoshi.order121Project.model;

/**
 * Created by Administrator on 2016/5/31.
 */
public class UpdateVersionModel {


    /**
     * resCode : SUCCESS
     * resnMsg : 操作成功
     * data : {"version":"2.3","deputy":"1","api":"v1","upload":"http://demo.86goodfood.com/upload/upload/1605311836202777.apk","intro":"test","updateTime":"1464690994"}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * version : 2.3
     * deputy : 1
     * api : v1
     * upload : http://demo.86goodfood.com/upload/upload/1605311836202777.apk
     * intro : test
     * updateTime : 1464690994
     */

    private DataEntity data;
    private String debugInfo;



    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }

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

    public DataEntity getData() {
        return data;
    }

    public String getDebugInfo() {
        return debugInfo;
    }

    public static class DataEntity {
        private String version;
        private String deputy;
        private String api;
        private String upload;
        private String intro;
        private String updateTime;

        public void setVersion(String version) {
            this.version = version;
        }

        public void setDeputy(String deputy) {
            this.deputy = deputy;
        }

        public void setApi(String api) {
            this.api = api;
        }

        public void setUpload(String upload) {
            this.upload = upload;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getVersion() {
            return version;
        }

        public String getDeputy() {
            return deputy;
        }

        public String getApi() {
            return api;
        }

        public String getUpload() {
            return upload;
        }

        public String getIntro() {
            return intro;
        }

        public String getUpdateTime() {
            return updateTime;
        }
    }
}
