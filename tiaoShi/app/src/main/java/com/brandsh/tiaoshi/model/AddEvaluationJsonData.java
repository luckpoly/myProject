package com.brandsh.tiaoshi.model;

/**
 * Created by 猪猪~ on 2016/3/12.
 */
public class AddEvaluationJsonData {


    /**
     * resCode : SUCCESS
     * resnMsg : 操作成功
     * data : {"evaluationId":"63"}
     * debugInfo : code=1|msg=
     */

    private String respCode;

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

    private String respMsg;
    /**
     * evaluationId : 63
     */

    private DataEntity data;
    private String debugInfo;


    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }
    public DataEntity getData() {
        return data;
    }

    public String getDebugInfo() {
        return debugInfo;
    }

    public static class DataEntity {
        private String evaluationId;

        public void setEvaluationId(String evaluationId) {
            this.evaluationId = evaluationId;
        }

        public String getEvaluationId() {
            return evaluationId;
        }
    }
}
