package com.brandsh.tiaoshi.model;

/**
 * Created by Administrator on 2016/12/2.
 */

public class OrderPayWayModel {


    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"payWay":"WX|ALIPAY","reco":"WX"}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * payWay : WX|ALIPAY
     * reco : WX
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
        private String payWay;
        private String reco;

        public String getPayWay() {
            return payWay;
        }

        public void setPayWay(String payWay) {
            this.payWay = payWay;
        }

        public String getReco() {
            return reco;
        }

        public void setReco(String reco) {
            this.reco = reco;
        }
    }
}
