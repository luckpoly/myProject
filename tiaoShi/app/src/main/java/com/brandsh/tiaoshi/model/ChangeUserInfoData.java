package com.brandsh.tiaoshi.model;

import java.util.List;

/**
 * Created by apple on 16/2/18.
 */
public class ChangeUserInfoData {


    /**
     * resCode : SUCCESS
     * resnMsg : 操作成功
     * data : []
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
    private List<?> data;



    public String getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
