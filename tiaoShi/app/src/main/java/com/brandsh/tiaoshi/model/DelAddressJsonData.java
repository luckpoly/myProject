package com.brandsh.tiaoshi.model;

import java.io.Serializable;

/**
 * Created by sisi on 16/3/15.
 */
public class DelAddressJsonData implements Serializable{


    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
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

    public String getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }
}
