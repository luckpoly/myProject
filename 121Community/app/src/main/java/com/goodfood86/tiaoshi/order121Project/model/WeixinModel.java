package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public class WeixinModel {


    /**
     * respCode : 0
     * respMsg : OK
     * data : {"appId":"wx2d919de3398e3c11","mchId":"1346612301","nonceStr":"tlbS9NS9jIUXpApg","prepayId":"wx201606231117242ed1acbe570080309161","tradeType":"APP"}
     * debugInfo : []
     */

    private int respCode;
    private String respMsg;
    /**
     * appId : wx2d919de3398e3c11
     * mchId : 1346612301
     * nonceStr : tlbS9NS9jIUXpApg
     * prepayId : wx201606231117242ed1acbe570080309161
     * tradeType : APP
     */

    private DataEntity data;
    private List<?> debugInfo;

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setDebugInfo(List<?> debugInfo) {
        this.debugInfo = debugInfo;
    }

    public int getRespCode() {
        return respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public DataEntity getData() {
        return data;
    }

    public List<?> getDebugInfo() {
        return debugInfo;
    }

    public static class DataEntity {
        private String appId;
        private String mchId;
        private String nonceStr;
        private String prepayId;
        private String tradeType;

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public void setMchId(String mchId) {
            this.mchId = mchId;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }

        public void setTradeType(String tradeType) {
            this.tradeType = tradeType;
        }

        public String getAppId() {
            return appId;
        }

        public String getMchId() {
            return mchId;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public String getTradeType() {
            return tradeType;
        }
    }
}
