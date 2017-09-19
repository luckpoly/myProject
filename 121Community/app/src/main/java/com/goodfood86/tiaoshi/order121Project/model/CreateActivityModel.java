package com.goodfood86.tiaoshi.order121Project.model;

/**
 * Created by Administrator on 2016/8/5.
 */
public class CreateActivityModel {


    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"customActivityId":34,"customActivityName":"中秋"}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * customActivityId : 34
     * customActivityName : 中秋
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
        private int customActivityId;
        private String customActivityName;

        public int getCustomActivityId() {
            return customActivityId;
        }

        public void setCustomActivityId(int customActivityId) {
            this.customActivityId = customActivityId;
        }

        public String getCustomActivityName() {
            return customActivityName;
        }

        public void setCustomActivityName(String customActivityName) {
            this.customActivityName = customActivityName;
        }
    }
}
