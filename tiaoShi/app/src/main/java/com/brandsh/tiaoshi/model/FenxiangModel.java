package com.brandsh.tiaoshi.model;

/**
 * Created by Administrator on 2016/8/17.
 */
public class FenxiangModel {

    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"getUrl":"http://test.86goodfood.com/order-share/get?code=94412eb239da063e150dd039bd9feb37","getCode":"162741"}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * getUrl : http://test.86goodfood.com/order-share/get?code=94412eb239da063e150dd039bd9feb37
     * getCode : 162741
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
        private String getUrl;
        private String getCode;

        public String getGetUrl() {
            return getUrl;
        }

        public void setGetUrl(String getUrl) {
            this.getUrl = getUrl;
        }

        public String getGetCode() {
            return getCode;
        }

        public void setGetCode(String getCode) {
            this.getCode = getCode;
        }
    }
}
