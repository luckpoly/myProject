package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by tiashiwang on 2016/4/5.
 */
public class InviteFriendModel {

    /**
     * respCode : 0
     * respMsg : 短信发送成功
     * data : ["18300709012"]
     * debugInfo : []
     */

    private int respCode;
    private String respMsg;
    private List<String> data;
    private List<?> debugInfo;

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public List<?> getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(List<?> debugInfo) {
        this.debugInfo = debugInfo;
    }
}
