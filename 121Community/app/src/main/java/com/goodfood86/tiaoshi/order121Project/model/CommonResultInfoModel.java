package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by Administrator on 2016/4/5.
 */
public class CommonResultInfoModel {

    /**
     * respCode : 0
     * respMsg : 上传成功
     * data : {}
     * debugInfo : []
     */

    private int respCode;
    private String respMsg;
    private List<?> debugInfo;

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
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

    public List<?> getDebugInfo() {
        return debugInfo;
    }
}
