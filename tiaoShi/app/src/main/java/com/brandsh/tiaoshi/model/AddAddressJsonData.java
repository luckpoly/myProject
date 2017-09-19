package com.brandsh.tiaoshi.model;

/**
 * Created by sisi on 16/3/9.
 */
public class AddAddressJsonData {


    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"addressId":72}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * addressId : 72
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
        private int addressId;

        public int getAddressId() {
            return addressId;
        }

        public void setAddressId(int addressId) {
            this.addressId = addressId;
        }
    }
}
