package com.brandsh.tiaoshi.model;

/**
 * Created by Administrator on 2017/1/18.
 */

public class OrderNoModel {

    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"paying":"1","pre":"0","sending":"0","evaluate":"0","after":"0"}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * paying : 1
     * pre : 0
     * sending : 0
     * evaluate : 0
     * after : 0
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
        private String paying;
        private String pre;
        private String sending;
        private String evaluate;
        private String after;

        public String getPaying() {
            return paying;
        }

        public void setPaying(String paying) {
            this.paying = paying;
        }

        public String getPre() {
            return pre;
        }

        public void setPre(String pre) {
            this.pre = pre;
        }

        public String getSending() {
            return sending;
        }

        public void setSending(String sending) {
            this.sending = sending;
        }

        public String getEvaluate() {
            return evaluate;
        }

        public void setEvaluate(String evaluate) {
            this.evaluate = evaluate;
        }

        public String getAfter() {
            return after;
        }

        public void setAfter(String after) {
            this.after = after;
        }
    }
}
