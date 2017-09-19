package com.brandsh.tiaoshi.model;

/**
 * Created by tiashiwang on 2016/6/12.
 */
public class WeiXinPayDate {

    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"appId":"wx1705d06974c1200e","mchId":"1346598601","nonceStr":"Fy1IGf8RP4XKTcHH","prepayId":"wx201606121127113d7a4dcd540137434718","tradeType":"APP"}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * appId : wx1705d06974c1200e
     * mchId : 1346598601
     * nonceStr : Fy1IGf8RP4XKTcHH
     * prepayId : wx201606121127113d7a4dcd540137434718
     * tradeType : APP
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
        private String appId;
        private String mchId;
        private String nonceStr;
        private String prepayId;
        private String tradeType;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getMchId() {
            return mchId;
        }

        public void setMchId(String mchId) {
            this.mchId = mchId;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }

        public String getTradeType() {
            return tradeType;
        }

        public void setTradeType(String tradeType) {
            this.tradeType = tradeType;
        }
    }
}
