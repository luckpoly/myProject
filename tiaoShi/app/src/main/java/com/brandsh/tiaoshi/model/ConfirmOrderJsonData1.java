package com.brandsh.tiaoshi.model;

/**
 * Created by sisi on 16/3/9.
 */
public class ConfirmOrderJsonData1 {


    /**
     * resCode : SUCCESS
     * resnMsg : 操作成功
     * data : {"orderCode":"16052714183082590063","total":306,"orderId":272}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;

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

    /**
     * orderCode : 16052714183082590063
     * total : 306
     * orderId : 272
     */

    private DataBean data;
    private String debugInfo;


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
        private String orderCode;
        private String total;
        private int orderId;

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }
    }
}
