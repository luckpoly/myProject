package com.brandsh.tiaoshi.model;

/**
 * Created by apple on 16/2/23.
 */
public class DeleteOlderJsonData {

    /**
     * resCode : SUCCESS
     * resnMsg : 操作成功
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

    private String debugInfo;



    public String getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }
}
