package com.brandsh.tiaoshi.model;

import java.util.List;

/**
 * Created by Administrator on 2016/12/1.
 */

public class ChongzhiModel {


    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : [{"invite":"","total":0.01,"createTime":1480643208},{"invite":"","total":0.01,"createTime":1480585794},{"invite":"","total":0.01,"createTime":1480585140},{"invite":"","total":0.01,"createTime":1480583949}]
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    private String debugInfo;
    /**
     * invite :
     * total : 0.01
     * createTime : 1480643208
     */

    private List<DataBean> data;

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

    public String getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String invite;
        private String total;
        private String createTime;
        private String topup;
        private String cashback;

        public String getCashback() {
            return cashback;
        }

        public void setCashback(String cashback) {
            this.cashback = cashback;
        }

        public String getTopup() {
            return topup;
        }

        public void setTopup(String topup) {
            this.topup = topup;
        }

        public String getInvite() {
            return invite;
        }

        public void setInvite(String invite) {
            this.invite = invite;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
