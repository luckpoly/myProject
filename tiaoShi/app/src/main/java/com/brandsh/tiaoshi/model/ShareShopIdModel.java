package com.brandsh.tiaoshi.model;

/**
 * Created by Administrator on 2016/11/16.
 */

public class ShareShopIdModel {

    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"shopId":261}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * shopId : 261
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
        private String shopId;

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }
    }
}
